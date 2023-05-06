import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { MatTabsModule } from '@angular/material/tabs';
import { MatMenuModule } from '@angular/material/menu';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { HttpClient } from '@angular/common/http';

interface Course {
  id: number;
  title: string;
  description: string;
  instructorId: number;
  keywords: string;
  price: number;
  isAuthorized: boolean;
  isPublished: boolean;
  isDeleted: boolean;
  categoryId: number;
  thumbnail: string;
  publishedTime: Date;
}

@Component({
  selector: 'app-course-details',
  templateUrl: './course-details.component.html',
  styleUrls: ['./course-details.component.scss']
})
export class CourseDetailsComponent {

course: Course = { id: 0, title: '', description: '', instructorId: 0, keywords: '', price: 0, isAuthorized: false, isPublished: false, isDeleted: false, categoryId: 0, thumbnail: '', publishedTime: new Date() };;
cId: number = 0;

constructor(private authService: AuthService, private http: HttpClient, private router: Router) { }

ngOnInit(): void {
  console.log(this.authService.currentUser);
  if (!this.authService.currentUser || this.authService.currentUser.userType !== 'student') {
    this.router.navigate(['/login']);
  } else if (this.authService.currentUser.user.blocked) {
        this.router.navigate(['/blocked']);
      }
     const courseID = JSON.parse(sessionStorage.getItem('viewCourseId') || '{}');
     console.log(courseID)
     this.cId = courseID;
     this.getCourse();
    }

  logout() {
        this.authService.logout();
        this.router.navigate(['/homepage']);
      }

  getCourse(){
  this.http
        .get<Course>(`http://localhost:8080/api/courses/getbyid/${this.cId}`)
        .subscribe((course) => {
          this.course = course;
        });
  }

  addToCart() {
      // Add the course to the cart
    }

    addToWishlist() {
      // Add the course to the wishlist
    }

}
