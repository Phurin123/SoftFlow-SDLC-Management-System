import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'An unexpected error occurred';

      if (error.error instanceof ErrorEvent) {
        errorMessage = `Error: ${error.error.message}`;
      } else {
        if (error.error?.message) {
          errorMessage = error.error.message;
        } else {
          switch (error.status) {
            case 400: errorMessage = 'Bad request'; break;
            case 401: errorMessage = 'Unauthorized access'; break;
            case 403: errorMessage = 'Access forbidden'; break;
            case 404: errorMessage = 'Resource not found'; break;
            case 422: errorMessage = error.error?.message || 'Business rule violation'; break;
            case 500: errorMessage = 'Internal server error'; break;
          }
        }
      }

      console.error('HTTP Error:', errorMessage, error);
      return throwError(() => new Error(errorMessage));
    })
  );
};
