import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

export interface Skill {
  id: number;
  name: string;
}

export interface JobCandidate {
  id: number;
  name: string;
  birth?: string;
  mail?: string;
  phoneNumber?: string;
  phone?: string;
  skills?: Skill[];
}

@Component({
  selector: 'app-hr-app',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class AppComponent implements OnInit {

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef) {}

  baseUrl = 'http://localhost:8080/api';

  candidates: JobCandidate[] = [];
  allSkills: Skill[] = [];

  editId: number | null = null;
  
  search = { 
    name: '', 
    skill: '' 
  };
  
  form = { 
    name: '', 
    birth: '', 
    mail: '', 
    phoneNumber: '', 
    skillName: '' 
  };


  ngOnInit() {
    this.refresh();
  } 

  refresh() {
    this.http.get<JobCandidate[]>(this.baseUrl + '/candidates').subscribe(res => {
      this.candidates = res;
      this.cdr.detectChanges();
    });

    this.http.get<Skill[]>(this.baseUrl + '/skills').subscribe(res => {
      this.allSkills = res;
      this.cdr.detectChanges();
    });
  }

  handleSaveCandidate() {
    if (this.editId !== null) {
      

      let url = this.baseUrl + '/candidates/' + this.editId;
      this.http.put(url, this.form).subscribe(() => {
        this.resetForm();
        this.refresh();
      });

    } else {
      

      let url = this.baseUrl + '/candidates';
      this.http.post(url, this.form).subscribe(() => {
        this.resetForm();
        this.refresh();
      });

    }
  }

  handleDeleteCandidate(id: number) {
    let answer = confirm("Da li ste sigurni da želite da obrišete kandidata?");
    
    if (answer === true) {
      let url = this.baseUrl + '/candidates/' + id;
      this.http.delete(url).subscribe(() => {
        this.refresh();
      });
    }
  }

  handleCreateSkill() {
    if (this.form.skillName === '') {
      return; 
    }
    
    let url = this.baseUrl + '/skills';
    let data = { name: this.form.skillName };
    
    this.http.post(url, data).subscribe(() => {
      this.form.skillName = '';
      this.refresh();
    });
  }

  handleAddSkillToCandidate(candidateId: number, event: Event) {
    let selectElement = event.target as HTMLSelectElement;
    let skillName = selectElement.value;
    
    if (skillName === '') {
      return;
    }
    
    let url = this.baseUrl + '/candidates/' + candidateId + '/skills-add/' + encodeURIComponent(skillName);
    
    this.http.post(url, {}).subscribe(() => {
      selectElement.value = '';
      this.refresh();
    });
  }

  handleRemoveSkillFromCandidate(candidateId: number, skillName: string) {
    let url = this.baseUrl + '/candidates/' + candidateId + '/skills-remove/' + skillName;
    
    this.http.post(url, {}).subscribe(() => {
      this.refresh();
    });
  }

  handleSearch() {
  // 1. Uvek vučemo sveže podatke sa servera da bismo imali punu listu za filtriranje
  this.http.get<JobCandidate[]>(this.baseUrl + '/candidates').subscribe(res => {
    let filteredResults = res;

    // 2. Primeni filter za IME (ako nije prazno)
    if (this.search.name !== '') {
      filteredResults = filteredResults.filter(c => 
        c.name.toLowerCase().includes(this.search.name.toLowerCase())
      );
    }

    // 3. Primeni filter za VEŠTINU (ako nije prazno)
    if (this.search.skill !== '') {
      filteredResults = filteredResults.filter(c => 
        c.skills?.some(s => s.name.toLowerCase().includes(this.search.skill.toLowerCase()))
      );
    }

    // 4. Update-uj tabelu i "probudi" Angular
    this.candidates = filteredResults;
    this.cdr.detectChanges();
  });
}
  resetSearch() {
    this.search.name = '';
    this.search.skill = '';
    this.refresh();
  }

  resetForm() {
    this.editId = null;
    this.form.name = '';
    this.form.birth = '';
    this.form.mail = '';
    this.form.phoneNumber = '';
    this.form.skillName = '';
  }

  populateEditForm(c: JobCandidate) {
    this.editId = c.id;
    if (c.name) {
      this.form.name = c.name;
    } else {
      this.form.name = '';
    }

    if (c.birth) {
      this.form.birth = c.birth;
    } else {
      this.form.birth = '';
    }

    if (c.mail) {
      this.form.mail = c.mail;
    } else {
      this.form.mail = '';
    }
    if (c.phoneNumber) {
      this.form.phoneNumber = c.phoneNumber;
    } else if (c.phone) {
      this.form.phoneNumber = c.phone;
    } else {
      this.form.phoneNumber = '';
    }
    
    this.form.skillName = '';
  }

  formatDate(birth?: string): string {
    if (birth !== undefined && birth !== null) {
      let part = birth.split('T');
      return part[0]; 
    } else {
      return 'Nepoznato';
    }
  }
}