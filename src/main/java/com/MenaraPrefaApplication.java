package com;

import com.dto.*;
import com.entity.type.UserType;
import com.repository.SocietyRepository;
import com.service.impl.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class MenaraPrefaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MenaraPrefaApplication.class, args);

	}
	@Bean
	CommandLineRunner start(UserServiceImpl userService1,RegionServiceImpl regionService, SocietyServiceImpl societyService, UserServiceImpl userService, SiteServiceImpl siteService, CommercialServiceImpl commercialService){
		return args -> {
//            regionService.AddRegion(new RegionAddDto("tanger-tétouan-al hoceima","Tanger"));
//            societyService.AddSociete(new SocietyInfoDto("Menara"));
//            siteService.add(new SiteAddDto("test11","test11",new LocalityDto(7,"ccc",15,17,"mohammeiad"),1,1));
//		      commercialService.add((new CommercialAddDto("zakaria","casa el Qods 1 ","Ziko2@gmail.com","066157525",1)));^^
//            userService.add(new UserAddDto("ccc","cccc",true,"rrrr",new Date(),UserType.admin,new ArrayList<Integer>(),1,null));

//			userService.add(new UserAddDto("mohamed","test123",true,"ccccc",new Date(), UserType.admin,new ArrayList<Integer>(),2,new HashSet<Integer>()));
		};
	}
}
