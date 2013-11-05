package com.systemsinmotion.orgchart.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.systemsinmotion.orgchart.entity.Department;
import com.systemsinmotion.orgchart.entity.Employee;
import com.systemsinmotion.orgchart.entity.JobTitle;
import com.systemsinmotion.orgchart.service.DepartmentService;
import com.systemsinmotion.orgchart.service.EmployeeService;
import com.systemsinmotion.orgchart.service.JobTitleService;
import com.systemsinmotion.orgchart.web.View;

@Controller
public class DefaultController {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory
			.getLogger(DefaultController.class);

	@Autowired
	EmployeeService employeeService;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	JobTitleService jobTitleService;
	
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String doGet() {
		return View.HOME;
	}
	
	@RequestMapping(value = "emps", method = RequestMethod.GET)
	public String doEmployees(Model model) {
		/* Provide information needed by the jsp in the form of attributes. */
		List<Employee> employees = employeeService.findAllEmployees();
		model.addAttribute("emps", employees);
		return View.EMPLOYEES;
	}
	
	@RequestMapping(value = "emps/{id}", method = RequestMethod.GET)
	public @ResponseBody
	String employeesPreFillForm(@PathVariable Integer id) {
		// Employee employee = this.employeeService.findById(id);

		// Gson gson = new Gson();
		// String json = gson.toJson(employee);
		// return json;
		return null;
	}

	@RequestMapping(value = "jobs", method = RequestMethod.GET)
	public String doJobTitles(Model model) {
		List<JobTitle> jobTitles = jobTitleService.findAllJobTitles();
		model.addAttribute("jobs", jobTitles);
		return View.JOB_TITLES;
	}
	
	@RequestMapping(value = "depts", method = RequestMethod.GET)
	public String doDepartments_GET(Model model) {
		 List<Department> departments = departmentService.findAllDepartments();
		 model.addAttribute("depts", departments);
		return View.DEPARTMENTS;
	}
	
	@RequestMapping(value = "depts", method = RequestMethod.POST)
	public String doDepartments_POST(Model model, Department newDepartment, @RequestParam("parent_id") Integer parentId ) {
		/* Ensure a valid department was sent in. */
		if(newDepartment == null) return View.DEPARTMENTS;
		
		/* Validate and set the parent id. */
		if(parentId > -1) {
			Department parentDepartment = departmentService.findDepartmentByID(parentId);
			newDepartment.setParentDepartment(parentDepartment);
		}
		
		/* Store the new department. */
		departmentService.storeDepartment(newDepartment);
		
		List<Department> departments = departmentService.findAllDepartments();
		model.addAttribute("depts", departments);
		
		return View.DEPARTMENTS;
	}
	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

}
