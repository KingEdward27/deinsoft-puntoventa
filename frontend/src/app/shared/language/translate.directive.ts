import { Input, Directive, ElementRef, OnChanges, OnInit, OnDestroy } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { translationNotFoundMessage } from 'src/app/config/translation.config';

/**
 * A wrapper directive on top of the translate pipe as the inbuilt translate directive from ngx-translate is too verbose and buggy
 */
@Directive({
  selector: '[translate]',
})
export class TranslateDirective implements OnChanges, OnInit, OnDestroy {
  @Input() translate!: string;
  @Input() translateValues?: { [key: string]: unknown };

  private readonly directiveDestroyed = new Subject<never>();

  constructor(private el: ElementRef, private translateService: TranslateService) {}

  ngOnInit(): void {
    this.translateService.onLangChange.pipe(takeUntil(this.directiveDestroyed)).subscribe(() => {
      this.getTranslation();
    });
    this.translateService.onTranslationChange.pipe(takeUntil(this.directiveDestroyed)).subscribe(() => {
      this.getTranslation();
    });
  }

  ngOnChanges(): void {
    this.getTranslation();
  }

  ngOnDestroy(): void {
    this.directiveDestroyed.next();
    this.directiveDestroyed.complete();
  }

  private getTranslation(): void {
    this.translateService
      .get(this.translate, this.translateValues)
      .pipe(takeUntil(this.directiveDestroyed))
      .subscribe(
        value => {
          this.el.nativeElement.innerHTML = value;
        },
        () => `${translationNotFoundMessage}[${this.translate}]`
      );
  }
}
