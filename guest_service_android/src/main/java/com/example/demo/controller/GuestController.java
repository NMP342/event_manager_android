package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Data;
import com.example.demo.model.Event;
import com.example.demo.model.Feedback;
import com.example.demo.model.Guest;
import com.example.demo.model.Place;
import com.example.demo.service.GuestService;

@RestController
@CrossOrigin
public class GuestController {
	
	@Autowired
	private GuestService guestService;
	
	@PostMapping("/send/data")
	public String sendData(@RequestBody Data data) {
		if(data.getGuests().isEmpty() || data.getEvent() == null) {
			System.out.println("dont receive");
			return "dont";
		}
		else {  
			List<Guest> guests = data.getGuests();
			//System.out.println(guests.get(0).toString());
			Event event = data.getEvent();
//			String places = getPlaces(event);
			
			for(Guest gt : guests) {
				Guest guest = guestService.getGuestByEmailAndEvent(gt.getEmail(), event.getId());
				if(guest == null) {
					gt.setIdEvent(event.getId());
					guestService.saveGuest(gt);
				}
			}
			
			int checkConfirm = 0;
			List<Guest> guestsFromDB = guestService.getAllGuestByIdEvent(event.getId());
			for(Guest gt : guestsFromDB) {
				if(gt.getConfirm() == 0) {
//					String content = setContent("http://192.168.0.102:8081/click?id=" + gt.getId(), gt, event, places);
					String content = setContent("http://192.168.0.102:8081/click?id=" + gt.getId(), gt, event);
					guestService.sendMail(gt.getEmail(), "Thư mời tham gia sự kiện", content);
					checkConfirm = 1;
				}
			}
			
			if(checkConfirm == 0) {
				System.out.println("accepted");
				return "0";
			}
			else {
				System.out.println("send again");
				return "1";
			}
		}
	}
	
	@PostMapping("/send/data/manual")
	public String sendDataManual(@RequestBody Data data) {
		if(data.getGuests().isEmpty() || data.getEvent() == null) {
			System.out.println("dont receive");
			return "dont";
		}
		else {  
			List<Guest> guests = data.getGuests();
			Guest guest = guests.get(0);
			Event event = data.getEvent();
//			String places = getPlaces(event);
			
			Guest guestFromDB = guestService.getGuestByEmailAndEvent(guest.getEmail(), event.getId());
			if(guestFromDB == null) {
				guest.setIdEvent(event.getId());
				guestService.saveGuest(guest);
			}
			
			guestFromDB = guestService.getGuestByEmailAndEvent(guest.getEmail(), event.getId());
			if(guestFromDB.getConfirm() == 0) {
//				String content = setContent("http://192.168.0.102:8081/click?id=" + 
//									guestFromDB.getId(), guestFromDB, event, places);
				String content = setContent("http://192.168.0.102:8081/click?id=" + 
						guestFromDB.getId(), guestFromDB, event);
				guestService.sendMail(guestFromDB.getEmail(), "Thư mời tham gia sự kiện", content);
				System.out.println("send again");
				return "1";
			}
			else {
				System.out.println("accepted");
				return "0";
			}
		}
	}
	
	@GetMapping("/click")
	public String saveStatusGuest(@RequestParam("id") int id){
		System.out.println("hello");
		guestService.changeStatus(id);
		return "sucessfull";
	}
	
	@PostMapping("/guests")
	public List<Guest> getAllGuest(@RequestBody int idEvent){
		List<Guest> list = guestService.getAllGuestByIdEvent(idEvent);
		return list;
	}
	
	@PostMapping("/send/feedback")
	public void sendFeedback(@RequestBody Feedback feedback) {
		int idEvent = feedback.getEvent().getId();
		String form = feedback.getLinkForm();
		List<Guest> list = guestService.getAllGuestByIdEvent(idEvent);
		
		String content = "<html><body><p>Cảm ơn bạn đã tham gia sự kiện của chúng tôi. <br/>" 
			+ "Hi vọng chúng tôi có thể nhận được phản hồi từ bạn để sự kiện lần sau được tổ chức tốt hơn. <br/>"
			+ "Vui lòng click vào link dưới đây để cho chúng tôi biết về suy nghĩ của bạn. Trân trọng cảm ơn. <br/>"
			+ "</p>"
	        + "<a href=\"" + form + "\" style=\"text-decoration: none; color: blue;\">Khảo sát ý kiến.</a>"
	        + "</body></html>";
		for(Guest gt : list) {
			if(gt.getConfirm() == 1) {
				guestService.sendMail(gt.getEmail(), "Khảo sát ý kiến về sự kiện", content);
			}
		}
	}
	
//	private String setContent(String url, Guest guest, Event event, String places) {
//		String content = "<html><body><p>Chào mừng bạn " + guest.getName() + " tham gia sự kiện: " + event.getName()
//				+ " của chúng tôi. Sự kiện sẽ bắt đầu từ " + event.getStart_time() + " ngày " + event.getStart_date()
//				+ " đến " + event.getEnd_time() + " ngày " + event.getEnd_date() + ", được tổ chức tại: " + places + ". "
//				+ "Nếu bạn muốn tham gia, hãy click nút dưới đây để đăng kí. Chúng tôi sẽ rất vui vì sự xuất hiện của bạn." 
//				+ "</p>"
//                + "<a href=\"" + url + "\"><button style=\"background-color: "
//                + "#4CAF50; color: white; padding: 10px 20px; border: none; border-radius: "
//                + "4px; cursor: pointer;\">Click me</button></a>"
//                + "</body></html>";
//		return content;
//	}
	
	private String setContent(String url, Guest guest, Event event) {
		String content = "<html><body><p>Chào mừng bạn " + guest.getName() + " tham gia sự kiện: " + event.getName()
				+ " của chúng tôi. Sự kiện sẽ bắt đầu từ " + event.getStart_time() + " ngày " + event.getStart_date()
				+ " đến " + event.getEnd_time() + " ngày " + event.getEnd_date() + ", được tổ chức tại: null. "
				+ "Nếu bạn muốn tham gia, hãy click nút dưới đây để đăng kí. Chúng tôi sẽ rất vui vì sự xuất hiện của bạn." 
				+ "</p>"
                + "<a href=\"" + url + "\"><button style=\"background-color: "
                + "#4CAF50; color: white; padding: 10px 20px; border: none; border-radius: "
                + "4px; cursor: pointer;\">Click me</button></a>"
                + "</body></html>";
		return content;
	}
	
//	public String getPlaces(Event event){
//		RestTemplate restTemplate = new RestTemplate();
//		
//		// nhớ đổi ip với port của máy bn nhó
//		String url = "http://192.168.22.101:8082/places?idEvent=" + event.getId();
//		ResponseEntity<List<Place>> response = restTemplate.exchange(url, HttpMethod.GET, null,
//				new ParameterizedTypeReference<List<Place>>(){});
//		
//		if (response.getStatusCode().is2xxSuccessful()) {
//		    List<Place> places = response.getBody();
//		    String res = "";
//		    for (Place place : places) {
//		        System.out.println("Place: " + place.getName());
//		        res += place.getName() + ", ";
//		    }
//		    res = res.substring(0, res.length()-2);
//		    return res;
//		} else {
//		    System.err.println("Error response from API: " + response.getStatusCodeValue());
//		}
//		return null;
//		
//	}

}