package api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.milestone.ticket.platform.model.Ticket;
import com.milestone.ticket.platform.service.TicketService;

@RestController
@CrossOrigin
@RequestMapping("api/tickets")
public class RestTicketController {

	@Autowired
	private TicketService service;

	@GetMapping("List-tickets")
	public ResponseEntity<List<Ticket>> index() {

		List<Ticket> listTickets;

		listTickets = service.findAllTickets();

		if (!listTickets.isEmpty())
			// Controllo se la lista non è vuote
			// Se super la condizione, restituisco un codice HTTP 200 con i dati
			return new ResponseEntity<List<Ticket>>(listTickets, HttpStatus.OK);

		// Se non entra nell'if, di default viene restitutito un codice HTTP 404
		return new ResponseEntity<List<Ticket>>(HttpStatus.NOT_FOUND);

	}
}