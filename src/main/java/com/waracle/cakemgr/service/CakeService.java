package com.waracle.cakemgr.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.exception.InvalidDataProvidedException;
import com.waracle.cakemgr.properties.CakeManagerProperties;
import com.waracle.cakemgr.service.repositories.CakeEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CakeService {

	Logger logger = LoggerFactory.getLogger(CakeService.class);

	private final CakeManagerProperties cakeManagerProperties;

	private final CakeEntityRepository cakeEntityRepository;

	private InputStream inputStream;
	private BufferedReader reader;

	public CakeEntity save(CakeEntity cakeEntity) {
		return cakeEntityRepository.save(cakeEntity);
	}

	public List<CakeEntity> getAllCakes() {
		return cakeEntityRepository.findAll();
	}

	public CakeEntity getCake(Long id) {
		return cakeEntityRepository.getById(id);
	}

	public void delete(Long id) {
		Optional.ofNullable(id).ifPresent(i -> cakeEntityRepository.deleteById(id));

	}

	public void retrieveCakeDataFromServer() throws InvalidDataProvidedException, ServletException {
		logger.info("Loading the Json Cake Data ...");
		JsonParser parser = null;
		
		try {
			URL url = new URL(cakeManagerProperties.getEndpoint());
			
			inputStream = url.openStream();
			reader = new BufferedReader(new InputStreamReader(inputStream));

			StringBuilder builder = new StringBuilder();
			String line = reader.readLine();
			
			while (line != null) {
				builder.append(line);
				line = reader.readLine();
			}

			logger.info("Parsing cake json");
			 parser = new JsonFactory().createParser(builder.toString());
			
			 if (JsonToken.START_ARRAY != parser.nextToken()) {
				throw new InvalidDataProvidedException("Unable to Parse the Json File");
			}

			JsonToken nextToken = parser.nextToken();
			while (nextToken == JsonToken.START_OBJECT) {
				logger.info("Creating cake entity");

				CakeEntity cakeEntity = new CakeEntity();
				logger.info("Cake Details Field: {}", parser.nextFieldName());
				cakeEntity.setCakeDetail(parser.nextTextValue());

				logger.info("Cake Description Field: {}", parser.nextFieldName());
				cakeEntity.setDescription(parser.nextTextValue());

				logger.info("Cake Image Field: {}", parser.nextFieldName());
				cakeEntity.setImage(parser.nextTextValue());
				
				logger.info("Cake Entity: {}", cakeEntity);

				CakeEntity savedEntity = save(cakeEntity);
				
				logger.info("Saved Cake Entity: {}", savedEntity);


				nextToken = parser.nextToken();
				logger.info("Json Next Token: {}", nextToken);

				nextToken = parser.nextToken();
				logger.info("Json Next Token: {}", nextToken);
			}

		} catch (Exception ex) {
			throw new ServletException(ex);
		}
		finally {
			if(parser != null) {
				try {
					parser.close();
				} catch (IOException e) {
					logger.error("ErrorClosing Parser: {}", e.getMessage());
				}
			}
		}

		logger.info("Initialisation Finished");
	}

}
