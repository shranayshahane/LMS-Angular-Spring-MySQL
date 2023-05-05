import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { MatTabsModule } from '@angular/material/tabs';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-add-video-material',
  templateUrl: './add-video-material.component.html',
  styleUrls: ['./add-video-material.component.scss']
})
export class AddVideoMaterialComponent {

title: string = '';
description: string = '';
cId: number = 0;
fileURL: string = '';

constructor(private authService: AuthService, private router: Router, private http: HttpClient) { }

ngOnInit(): void {
  console.log(this.authService.currentUser);
  if (!this.authService.currentUser || this.authService.currentUser.userType !== 'instructor') {
    this.router.navigate(['/login']);
  } else if (!this.authService.currentUser.user.authorized) {
    this.router.navigate(['/unauthorized']);
  } else if (this.authService.currentUser.user.blocked) {
        this.router.navigate(['/blocked']);
      }
  const courseID = JSON.parse(sessionStorage.getItem('addMaterial') || '{}');
  console.log(courseID);
  this.cId = courseID;
   console.log(this.cId);
}

submit() {
    const requestBody = {
          title: this.title,
          description: this.description,
          courseId: this.cId,
          materialType: 'video',
          fileUrl: this.fileURL,
          sequenceNumber: ''
        };
    console.log(requestBody);
    this.http.post<any>('http://localhost:8080/api/coursematerial/add', requestBody).subscribe(
      () => {
        console.log('Material Uploaded successfully!');
        this.router.navigate(['/edit-material']);
      },
      (error) => {
        console.error('Error uploading Material: ', error);
      }
    );
  }

  onFileSelected(event: any): void {
      const file: File = event.target.files[0];
      const formData = new FormData();
      formData.append('file', file);
      this.http.post('http://localhost:8080/api/coursematerial/upload', formData, { responseType: 'text' }).subscribe(
        (response) => {
          console.log(response);
          const fileName = response.split('\\').pop(); // extract file name from response string
          this.fileURL = 'http://127.0.0.1:8887/Material/' + fileName; // construct thumbnail URL
        },
        (error) => {
          console.error('Error uploading file:', error);
        }
      );
    }


  logout() {
      this.authService.logout();
      this.router.navigate(['/homepage']);
    }

}

