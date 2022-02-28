package com.gleenpeltroche.iniciosesionregistro.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gleenpeltroche.iniciosesionregistro.models.LoginUser;
import com.gleenpeltroche.iniciosesionregistro.models.Project;
import com.gleenpeltroche.iniciosesionregistro.models.Task;
import com.gleenpeltroche.iniciosesionregistro.models.User;
import com.gleenpeltroche.iniciosesionregistro.repositories.TaskRepository;
import com.gleenpeltroche.iniciosesionregistro.services.ProjectService;
import com.gleenpeltroche.iniciosesionregistro.services.UserService;

@Controller
public class HomeController {

 @Autowired
 private UserService userServ;
 @Autowired
 private ProjectService projectServ;
 @Autowired
 private TaskRepository taskRepository;
 
 @GetMapping("")
 public String index(Model model, HttpSession session) {
	 User userSession = (User) session.getAttribute("user");
	 if(userSession != null){
	     return "redirect:/dashboard";
	 }
     model.addAttribute("newUser", new User());
     model.addAttribute("newLogin", new LoginUser());
     return "index.jsp";
 }
 
 @PostMapping("/login")
 public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
         BindingResult result, Model model, HttpSession session) {

     if(result.hasErrors()) {
    	 model.addAttribute("newUser", new User());
         return "index.jsp";
     }
	 
     User user = userServ.login(newLogin, result);
     if(user == null) {
    	 result.rejectValue("email", "Matches", "El nombre usuario es incorrecto.");
    	 model.addAttribute("newUser", new User());
    	 return "index.jsp";
     }
     
     if(!BCrypt.checkpw(newLogin.getPassword(), user.getPassword())) {
    	 result.rejectValue("password", "Matches", "El password es incorrecto.");
    	 model.addAttribute("newUser", new User());
    	 return "index.jsp";
     }
     session.setAttribute("user", user);
     return "redirect:/";
 }
 
 @PostMapping("/register")
 public String register(@Valid @ModelAttribute("newUser") User newUser, 
         BindingResult result, Model model, HttpSession session) {
     
     if(result.hasErrors()) {
    	 model.addAttribute("newLogin", new LoginUser());
         return "index.jsp";
     }
     
     if(!newUser.getPassword().equals(newUser.getConfirm())) {
    	 result.rejectValue("confirm", "Matches", "La contraseña de confirmación debe coincidir");
    	 model.addAttribute("newLogin", new LoginUser());
    	 return "index.jsp";
     }
     
     User user = userServ.register(newUser, result);
     if(user == null) {
    	 result.rejectValue("email", "Matches", "El email ya se encuentra registrado.");
    	 model.addAttribute("newLogin", new LoginUser());
    	 return "index.jsp";
     }
     session.setAttribute("user", user);
     return "redirect:/";
 }
 
 @GetMapping("/projects/new")
 public String projectnew(Model model, HttpSession session) {
	 User userSession = (User) session.getAttribute("user");
	 if(userSession == null){
		 return "redirect:/";
	 }
	 model.addAttribute("user", userSession);
     return "newproject.jsp";
 }
 
 @PostMapping("/projects/create")
 public String projectcreate(
	 @RequestParam("title") String title,
	 @RequestParam("description") String description,
	 @RequestParam("dueDate") String dueDate,
	 RedirectAttributes redirectAttributes,
     Model model,
     HttpSession session ) {
	 User userSession = (User) session.getAttribute("user");
	 if(userSession == null){
		 return "redirect:";
	 }
	 if(title.equals("") || description.equals("")) {
		 redirectAttributes.addFlashAttribute("title_error", "El titulo no debe estar vacío.");
		 redirectAttributes.addFlashAttribute("description_error", "La descripción no debe estar vacía.");
		 return "redirect:/projects/new";
	 }
	 Project project = new Project();
	 try {
		 SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		 Date date = formatter.parse(dueDate);
		 Date now = new Date();
		 if(date.getTime() - now.getTime() > 0) {
			 project.setDueDate(date);
		 }else {
			 redirectAttributes.addFlashAttribute("dueDate_error", "La fecha no puede ser pasada.");
			 return "redirect:/projects/new";
		 }
	} catch (Exception e) {
		 redirectAttributes.addFlashAttribute("dueDate_error", "No es un formato de fecha válido.");
		 return "redirect:/projects/new";
	}
	 project.setTitle(title);
	 project.setDescription(description);
	 project.setUser(userSession);
	 List<User> user = new ArrayList<User>();
	 user.add(userSession);
	 project.setTeamUsers(user);
     projectServ.saveProject(project);
     return "redirect:/dashboard";
 }
 
 @GetMapping("/projects/edit/{id}")
 public String projecteditbyid(
		 Model model, 
		 HttpSession session,
		 @PathVariable("id") Long id ) {
	 User userSession = (User) session.getAttribute("user");
	 if(userSession == null){
		 return "redirect:/";
	 }
	 Project project = projectServ.findProjectById(id);
	 if(project == null) {
		 return "redirect:/dashboard";
	 }
	 DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	 String strDueDate = dateFormat.format(project.getDueDate());  
	 model.addAttribute("project", project);
	 model.addAttribute("strDueDate", strDueDate);
     return "editproject.jsp";
 }
 
 @PostMapping("/projects/edit")
 public String projectedit(
	 @RequestParam("id") Long id,
	 @RequestParam("title") String title,
	 @RequestParam("description") String description,
	 @RequestParam("dueDate") String dueDate,
	 RedirectAttributes redirectAttributes,
     Model model,
     HttpSession session ) {
	 User userSession = (User) session.getAttribute("user");
	 if(userSession == null){
		 return "redirect:";
	 }
	 if(title.equals("") || description.equals("")) {
		 redirectAttributes.addFlashAttribute("title_error", "El titulo no debe estar vacío.");
		 redirectAttributes.addFlashAttribute("description_error", "La descripción no debe estar vacía.");
		 return "redirect:/projects/edit/"+id;
	 }
	 Project project = projectServ.findProjectById(id);
	 try {
		 SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		 Date date = formatter.parse(dueDate);
		 Date now = new Date();
		 if(date.getTime() - now.getTime() > 0) {
			 project.setDueDate(date);
		 }else {
			 redirectAttributes.addFlashAttribute("dueDate_error", "La fecha no puede ser pasada.");
			 return "redirect:/projects/edit/"+id;
		 }
	} catch (Exception e) {
		 redirectAttributes.addFlashAttribute("dueDate_error", "No es un formato de fecha válido.");
		 return "redirect:/projects/edit/"+id;
	}
	 project.setTitle(title);
	 project.setDescription(description);
	 project.setUser(userSession);
     projectServ.saveProject(project);
     return "redirect:/dashboard";
 }
 
 @GetMapping("/projects/{id}")
 public String projectbyid(
		 Model model,
		 HttpSession session,
		 @PathVariable("id") Long id ) {
	 User userSession = (User) session.getAttribute("user");
	 if(userSession == null){
		 return "redirect:/";
	 }
	 Project project = projectServ.findProjectById(id);
	 if(project == null) {
		 return "redirect:/dashboard";
	 }
	 DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	 String strDueDate = dateFormat.format(project.getDueDate());
	 model.addAttribute("user", userSession);
	 model.addAttribute("project", project);
	 model.addAttribute("strDueDate", strDueDate);
     return "projectbyid.jsp";
 }
 
 @PostMapping("/projects/delete")
 public String projectedit(
	 @RequestParam("id") Long id,
     HttpSession session ) {
	 User userSession = (User) session.getAttribute("user");
	 if(userSession == null){
		 return "redirect:";
	 }
     projectServ.deleteProject(id);
     return "redirect:/dashboard";
 }
 
 @GetMapping("/logout")
 public String logout(HttpSession session) {
	 session.removeAttribute("user");
	 return "redirect:/";
 }
 
 @PostMapping("/projects/addprojectuser")
 public String addBorrow(
		 @RequestParam("project_id") Long project_id,
		 HttpSession session
	 ) {
	 User userSession = (User) session.getAttribute("user");
	 if(userSession == null){
		 return "redirect:/";
	 }
	 Project project = projectServ.findProjectById(project_id);
	 project.getTeamUsers().add(userSession);
	 projectServ.saveProject(project);
     return "redirect:/dashboard";
 }
 
 @PostMapping("/projects/removeprojectuser")
 public String removeborrow(
		 @RequestParam("project_id") Long project_id,
		 HttpSession session
	 ) {
	 User userSession = (User) session.getAttribute("user");
	 if(userSession == null){
		 return "redirect:/";
	 }
	 Project project = projectServ.findProjectById(project_id);
	 for (int i = 0; i < project.getTeamUsers().size(); i++) {
		 if(project.getTeamUsers().get(i).getId() == userSession.getId()) {
			 project.getTeamUsers().remove(i);
			 break;
		 }
     }
	 projectServ.saveProject(project);
     return "redirect:/dashboard";
 }
 
 @GetMapping("/dashboard")
 public String home(Model model, HttpSession session) {
	 User userSession = (User) session.getAttribute("user");
	 if(userSession == null){
		 return "redirect:/";
	 }
	 model.addAttribute("user", userSession);
	 List<Project> projects = projectServ.getAllProjects();
	 List<Project> noProjectsByUser = new ArrayList<>(projects);
	 List<Project> projectsByUser = new ArrayList<>();
	 for (int i = 0; i < projects.size(); i++) {
		 if(projects.get(i).getTeamUsers().size() > 0) {
			 List<User> users = projects.get(i).getTeamUsers();
			 for(int j = 0; j < users.size(); j++) {
				 if(users.get(j).getId() == userSession.getId()) {
					 projectsByUser.add(projects.get(i));
					 noProjectsByUser.remove(projects.get(i));
				 }
			 }
		 }
     }
	 model.addAttribute("noProjectByUser", noProjectsByUser);
	 model.addAttribute("projectsByUser", projectsByUser);
     return "home.jsp";
 }
 
 @GetMapping("/projects/{id}/tasks")
 public String tasks(
	 Model model, 
	 HttpSession session,
	 @PathVariable("id") Long id) {
	 User userSession = (User) session.getAttribute("user");
	 if(userSession == null){
		 return "redirect:/";
	 }
	 Project project = projectServ.findProjectById(id);
	 model.addAttribute("user", userSession);
	 model.addAttribute("project", project);
     return "taskbyproject.jsp";
 }
 
 @PostMapping("/tasks/create")
 public String addtask(
		 @RequestParam("project_id") Long project_id,
		 @RequestParam("description") String description,
		 HttpSession session
	 ) {
	 User userSession = (User) session.getAttribute("user");
	 if(userSession == null){
		 return "redirect:/";
	 }
	 Project project = projectServ.findProjectById(project_id);
	 User user = userServ.getById(userSession.getId());
	 Task task = new Task();
	 task.setDescription(description);
	 task.setProject(project);
	 task.setUser(user);
	 task.setDate(new Date());
	 taskRepository.save(task);
     return "redirect:/projects/"+project_id+"/tasks";
 }
}