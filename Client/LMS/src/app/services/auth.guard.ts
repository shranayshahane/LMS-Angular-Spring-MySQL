import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (this.authService.currentUser) {
      return true;
    } else {
      // If there is no currentUser, navigate to the login page
      this.router.navigate(['/login']);
      return false;
    }
  }
}
