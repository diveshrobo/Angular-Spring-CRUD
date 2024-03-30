import { Pipe, PipeTransform } from '@angular/core';
import { Employee } from './employee';

@Pipe({
  name: 'searchFilter'
})
export class SearchFilterPipe implements PipeTransform {

  transform(Employee: Employee[],searchValue:string): Employee[] {
    
    if(!Employee || !searchValue ){
      return Employee;
    }
    return Employee.filter(employee => employee.firstName?.toLocaleLowerCase().includes(searchValue.toLocaleLowerCase()));

    

  
}

}


