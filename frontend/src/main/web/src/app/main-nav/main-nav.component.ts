import {AfterViewChecked, ChangeDetectorRef, Component, Renderer2} from '@angular/core';

@Component({
  selector: 'app-main-nav',
  templateUrl: './main-nav.component.html',
  styleUrls: ['./main-nav.component.scss']
})
export class MainNavComponent implements AfterViewChecked {

  // md && gt-md media query
  mediaQuery = window.matchMedia('(min-width: 960px)');
  navHidden = true;
  test = false;

  toggleNavigation() {
    this.navHidden = !this.navHidden;
  }

  constructor(private cdr: ChangeDetectorRef, private renderer: Renderer2) {
  }

  ngAfterViewChecked() {
    if (this.mediaQuery.matches) {
      this.navHidden = true;
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
