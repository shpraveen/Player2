package com.justEatAway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.justEatAway.Bean;

@RestController
@RequestMapping("player2")
public class ApplicationController {

	@Autowired
	private RestTemplate restTemp;

	@GetMapping("/process")
	public String process() {
		return "Processing...";
	}

	@PostMapping("/player2FirstInput")
	public String player1FirstInput(@RequestParam("num") int i) {

		if (i % 1 == 0) {
			boolean test;
			int cnt;
			int num = 0;

			if ((i + 1) % 3 == 0) {
				num = (i + 1) / 3;
			} else if ((i + 0) % 3 == 0) {
				num = (i + 0) / 3;
			} else if ((i - 1) % 3 == 0) {
				num = (i - 1) / 3;
			}

			if (num == 1) {

				test = true;
				System.out.println("Player  2 is won");
				return "Player 2 is won";
			} else {
				test = false;

				Bean b = new Bean();
				b.setNum(Integer.toString(num));
				String response;

				Bean player1Response = restTemp.postForObject("http://localhost:8997/player1/player1Value", b,
						Bean.class);
				response = player1Response.getNum();

				if (player1Response.equals("1")) {
					System.out.println("Pleyer 1 is won");
					return "Pleyer 1 is won";
				}

			}

		} else {
			return "Please enter the correct Number";
		}

		return null;

	}

	@PostMapping(value = "/player2Value", produces = { "application/json", "application/xml" })
	public ResponseEntity<Object> processPlayer2Value(@RequestBody Bean bb) throws JsonProcessingException {

		Bean b2 = new Bean();

		int i = Integer.parseInt(bb.getNum());

		if (i % 1 == 0) {

			boolean test;
			int cnt;
			int num = 0;

			if (i == 1) {
				b2.setStatus("Player 2 is won");
				ResponseEntity<Object> entity = new ResponseEntity<>(b2, HttpStatus.OK);
				return entity;
			}

			if ((i + 1) % 3 == 0) {
				num = (i + 1) / 3;
			} else if ((i + 0) % 3 == 0) {
				num = (i + 0) / 3;
			} else if ((i - 1) % 3 == 0) {
				num = (i - 1) / 3;
			}

			if (num == 1) {

				test = true;
				b2.setStatus("Player 2 is won");
				ResponseEntity<Object> entity = new ResponseEntity<>(b2, HttpStatus.OK);
				return entity;
			} else {
				test = false;

				Bean b = new Bean();
				b.setNum(Integer.toString(num));
				String response;

				Bean player1Response = restTemp.postForObject("http://localhost:8997/player1/player1Value", b,
						Bean.class);
				response = player1Response.getNum();

				if (player1Response.equals("1")) {
					System.out.println("Player 1 is won");
					Bean b3 = new Bean();
					b3.setStatus("Player 1 is won");

					ResponseEntity<Object> entity = new ResponseEntity<>(b3, HttpStatus.CONTINUE);
					return entity;

				}

			}

			int msg;
			if (test) {
				msg = 1;
			} else {
				msg = 0;
			}

		} else

		{
			b2.setStatus("failed");
			ResponseEntity<Object> entity = new ResponseEntity<>(b2, HttpStatus.BAD_REQUEST);
			return entity;
		}
		b2.setStatus("Game is on");
		ResponseEntity<Object> entity = new ResponseEntity<>(b2, HttpStatus.CONTINUE);
		return entity;
	}

}
