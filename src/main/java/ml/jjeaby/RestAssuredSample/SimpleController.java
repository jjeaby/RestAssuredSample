package ml.jjeaby.RestAssuredSample;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

	@RequestMapping("/")
	public String home() {
		return "안녕 Spring Boot!";
	}

	@GetMapping("/rest/info/{bookTitle}")
	public Book findByTitle(@PathVariable String bookTitle) {
		Book book = new Book();

		book.setTitle(bookTitle);
		book.setPublisher("ACT 출판사");
		book.setWriter("Jin");
		book.setStatus("instock");
		return book;
	}

	@PostMapping("/rest/add")
	public Book sayHello(@RequestBody Book book) {
		book.setStatus("added");
		return book;
	}
}