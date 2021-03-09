import {AfterViewChecked, ChangeDetectorRef, Component, Renderer2} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.scss'],
  animations: [
    trigger('mobileNavInOut', [
      transition(':enter', [
        style({ transform: 'translateY(-100%)' }),
        animate('200ms ease-out',
          style({ transform: 'translateY(0)'}))
      ]),
      transition(':leave', [
        style({ transform: 'translateY(0)'}),
        animate('200ms ease-in',
          style({ transform: 'translateY(-100%)' }))
      ])
    ])
  ]
})
export class MainNavComponent implements AfterViewChecked {

  // md && gt-md media query
  mediaQuery = window.matchMedia('(min-width: 960px)');
  mobileNavHidden = true;
  test = false;

  toggleNavigation() {
    this.mobileNavHidden = !this.mobileNavHidden;
  }

  constructor(private cdr: ChangeDetectorRef, private renderer: Renderer2) {
  }

  ngAfterViewChecked() {
    if (this.mediaQuery.matches) {
      this.mobileNavHidden = true;
      this.cdr.detectChanges();
    }
  }

  toggleDarkMode() {
    const clazz = 'dark-mode';
    if (document.body.classList.contains(clazz)) {
      this.renderer.removeClass(document.body, clazz);
    } else {
      this.renderer.addClass(document.body, clazz);
    }
  }
}
