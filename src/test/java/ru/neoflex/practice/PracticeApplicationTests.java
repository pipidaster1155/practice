package ru.neoflex.practice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class PracticeApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@Test
	void SumText() throws Exception {
		this.mockMvc
				.perform(get("/plus/1/1")) // perform a request that can be chained
				.andDo(print()) // we log the result
				.andExpect(content().string(containsString("2"))); // we check that the Body of the answer contains our expectation
	}
	@Test
	void DifText() throws Exception {
		this.mockMvc
				.perform(get("/minus/1/1")) // perform a request that can be chained
				.andDo(print()) // we log the result
				.andExpect(content().string(containsString("0"))); // we check that the Body of the answer contains our expectation
	}
}
