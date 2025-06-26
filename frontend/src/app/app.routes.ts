import { Routes } from '@angular/router';
import { AbiturientsComponent } from './abiturients/abiturients.component';
// Update the import path below if the file is in a subfolder or has a different name/casing
import { DirectionStatementsComponent } from './direction-statements/direction-statements.component';
import { FileUploadComponent } from './file-upload/file-upload.component';
import { InstituteDirectionsComponent } from './institute-directions/institute-directions.component';
import { InstituteListComponent } from './institutes/institute-list.component';
import { StatementListComponent } from './statements/statement-list.component';

export const routes: Routes = [
    { path: '', component: InstituteListComponent },
    { path: 'upload', component: FileUploadComponent },
    { path: 'abiturients', component: AbiturientsComponent },
    { path: 'statements/:personalNumber', component: StatementListComponent },
    { path: 'institutes/:id', component: InstituteDirectionsComponent },
    { path: 'directions/:directionName', component: DirectionStatementsComponent }
];