import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppRoutingModule} from '@app/app-routing.module';
import {AppComponent} from '@app/app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {SharedModule} from '@app/shared/shared.module';
import {LayoutModule} from '@angular/cdk/layout';
import {MainNavComponent} from '@app/main-nav/main-nav.component';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {StoreModule} from "@ngrx/store";
import {EffectsModule} from "@ngrx/effects";
import {StoreDevtoolsModule} from "@ngrx/store-devtools";
import {environment} from "@env/environment";
import {TranslateLoader, TranslateModule, TranslateService} from '@ngx-translate/core';
import {TranslateHttpLoader} from "@ngx-translate/http-loader";
import { NotifierModule } from 'angular-notifier';
import {MatPaginatorIntl} from "@angular/material/paginator";
import {MatPaginatorIntlCustom} from "@app/shared/services/mat-paginator-intl-custom.service";

@NgModule({
  declarations: [
    AppComponent,
    MainNavComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    SharedModule,
    LayoutModule,
    HttpClientModule,
    StoreModule.forRoot({}),
    EffectsModule.forRoot([]),
    StoreDevtoolsModule.instrument({
      maxAge: 25,
      logOnly: environment.production
    }),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      },
      defaultLanguage: 'pl'
    }),
    NotifierModule.withConfig({
      position: {
        horizontal: {
          position: 'right',
          distance: 28
        },
        vertical: {
          position: 'top',
          distance: 70
        }
      }
    })
  ],
  providers: [
    {
      provide: MatPaginatorIntl,
      useFactory: (translate: TranslateService) => {
        return new MatPaginatorIntlCustom(translate);
      },
      deps: [TranslateService]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

export function HttpLoaderFactory(http: HttpClient): TranslateHttpLoader {
  return new TranslateHttpLoader(http);
}
