import {AfterViewInit, Component, OnInit} from '@angular/core';
import {AppService} from '@services/app.service';
import {DateTime} from 'luxon';

@Component({
    selector: 'app-user',
    templateUrl: './user.component.html',
    styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
    public user;
    public perfil :any[] = [];
    public empresa;
    public local;

    constructor(private appService: AppService) {}
    ngOnInit() {
        // console.log("asdf");
        // console.log(this.appService.user);
        
        this.user = this.appService.user;
        this.empresa = this.user.profile.split("|")[3] == "*" ? "TODOS" : this.user.profile.split("|")[3]
        if (this.user.profile.split("|")[4] == "*" ) {
            this.local = "TODOS"
            this.perfil.push(this.user.profile.split("|")[0] + "|" + "TODOS LOS LOCALES" )
        } else {
            if (this.user.profile.split(";").length > 1){
                let arrayLocales = this.user.profile.split(";");
                arrayLocales.forEach(element => {
                    this.perfil.push(element.split("|")[4] + "|"+ element.split("|")[0])
                });
            } else{
                this.perfil.push(this.user.profile.split("|")[0] + "|" + this.user.profile.split("|")[4] )
            }
        }
        // console.log("asd: ", this.user);
        
    }

    logout() {
        this.appService.logout();
    }

    formatDate(date) {
        return DateTime.fromISO(date).toFormat('dd LLL yyyy');
    }
}
