import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { UtilService } from '@services/util.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ventas-app';
  mySubscription;
  
  constructor(private router: Router, private translate: TranslateService, private utilService: UtilService) {
    this.setAppLanguage();
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.mySubscription = this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        // Trick the Router into believing it's last link wasn't previously loaded
        this.router.navigated = false;
      }
    });
  }
  
  setAppLanguage() {
    this.translate.addLangs(['es', 'en']);
    this.translate.setDefaultLang("es");
    let lang = localStorage.getItem('lang');
    this.translate.use(lang ? lang : "es");
    // this.translate.use(this.translate.getBrowserLang());

    // const browserLang = this.translate.getBrowserLang();
    // this.translate.use(browserLang.match(/es|en/) ? browserLang : 'en');
  }
}
