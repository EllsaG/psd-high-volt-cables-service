package psdhighvoltcablesservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.psdhighvoltcablesservice.PsdHighVoltCablesServiceApplication;
import com.project.psdhighvoltcablesservice.controller.dto.*;
import com.project.psdhighvoltcablesservice.entity.*;
import com.project.psdhighvoltcablesservice.repository.*;
import com.project.psdhighvoltcablesservice.rest.PowerTransformerByIdResponseDTO;
import com.project.psdhighvoltcablesservice.rest.PowerTransformers;
import com.project.psdhighvoltcablesservice.rest.PowerTransformersServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import psdhighvoltcablesservice.config.SpringH2DatabaseConfig;

import org.junit.jupiter.api.Assertions;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {PsdHighVoltCablesServiceApplication.class, SpringH2DatabaseConfig.class})
@Transactional
public class HighVoltCablesControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private HighVoltCablesSelectionRepository highVoltCablesSelectionRepository;
    @Autowired
    private HighVoltInformationRepository highVoltInformationRepository;
    @Autowired
    private HighVoltInformationInductiveImpedanceAreasRepository highVoltInformationInductiveImpedanceAreasRepository;
    @Autowired
    private InductiveImpedanceAreasRepository inductiveImpedanceAreasRepository;
    @Autowired
    private HighVoltCablesRepository highVoltCablesRepository;
    @MockBean
    private PowerTransformersServiceClient powerTransformersServiceClient;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @Sql(scripts = {"/sql/clearHighVoltInformationDB.sql", "/sql/clearHighVoltCablesDB.sql","/sql/clearHighVoltCablesSelectionDB.sql",
            "/sql/addHighVoltInformation.sql", "/sql/addHighVoltCablesInformation.sql","/sql/addHighVoltCablesSelectionInformation.sql"})
    public void getAllHighVoltCableSelectionInformation() throws Exception {
        Mockito.when(powerTransformersServiceClient.getPowerTransformersInformationById(ArgumentMatchers.anyShort()))
                .thenReturn(createPowerTransformerByIdResponseDTO());

        MvcResult mvcResult = mockMvc.perform(get("/selectionInformation/getAllInformation"))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        HighVoltCablesSelectionInformationResponseDTO highVoltCablesSelectionInformationResponseDTO = objectMapper
                .readValue(body, HighVoltCablesSelectionInformationResponseDTO.class);
        List<HighVoltCablesSelection> highVoltCablesSelectionList = highVoltCablesSelectionInformationResponseDTO.getHighVoltCablesSelectionList();
        List<HighVoltCablesSelection> highVoltCablesSelectionRepositoryList = highVoltCablesSelectionRepository.findAll();

        Assertions.assertEquals(highVoltCablesSelectionList.get(0).getHighVoltCableSelectionId(),
                highVoltCablesSelectionRepositoryList.get(0).getHighVoltCableSelectionId());
        Assertions.assertEquals(highVoltCablesSelectionList.get(1).getMinCableSectionForSelect(),
                highVoltCablesSelectionRepositoryList.get(1).getMinCableSectionForSelect());
    }


    @Test
    @Sql(scripts = {"/sql/clearHighVoltInformationDB.sql", "/sql/clearHighVoltCablesDB.sql","/sql/clearHighVoltCablesSelectionDB.sql",
            "/sql/addHighVoltCablesSelectionInformation.sql","/sql/addHighVoltInformation.sql", "/sql/addHighVoltCablesInformation.sql"})
    public void getAllHighVoltCables() throws Exception {
        Mockito.when(powerTransformersServiceClient.getPowerTransformersInformationById(ArgumentMatchers.anyShort()))
                .thenReturn(createPowerTransformerByIdResponseDTO());

        MvcResult mvcResult = mockMvc.perform(get("/getAllInformation"))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        HighVoltCablesResponseDTO highVoltCablesResponseDTO = objectMapper
                .readValue(body, HighVoltCablesResponseDTO.class);

        List<HighVoltCables> highVoltCablesRepositoryList = highVoltCablesRepository.findAll();

        Assertions.assertEquals(highVoltCablesRepositoryList.get(0).getHighVoltCablesId(),
                highVoltCablesResponseDTO.getHighVoltCablesList().get(0).getHighVoltCablesId());
        Assertions.assertEquals(highVoltCablesRepositoryList.get(0).getCableType(),
                highVoltCablesResponseDTO.getHighVoltCablesList().get(0).getCableType());

        Assertions.assertEquals(highVoltCablesRepositoryList.get(1).getHighVoltCablesId(),
                highVoltCablesResponseDTO.getHighVoltCablesList().get(1).getHighVoltCablesId());
        Assertions.assertEquals(highVoltCablesRepositoryList.get(1).getCableType(),
                highVoltCablesResponseDTO.getHighVoltCablesList().get(1).getCableType());
    }

    @Test
    @Sql(scripts = { "/sql/clearHighVoltInformationDB.sql", "/sql/clearHighVoltCablesDB.sql","/sql/clearHighVoltCablesSelectionDB.sql",
            "/sql/addHighVoltInformation.sql", "/sql/addHighVoltCablesSelectionInformation.sql"})
    public void createNewHighVoltCable() throws Exception {


        String REQUEST = createHighVoltCablleRequestAsString();

        MvcResult mvcResult = mockMvc.perform(put("/create/highVoltCable")
                        .content(REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        HighVoltCablesResponseDTO highVoltCablesResponseDTO = objectMapper
                .readValue(body, HighVoltCablesResponseDTO.class);

        HighVoltCables highVoltCablesRepositoryById = highVoltCablesRepository.findById((short) 3)
                .orElseThrow(() -> new NoSuchElementException("No value present"));

        Assertions.assertEquals(highVoltCablesResponseDTO.getHighVoltCablesList().get(0).getHighVoltCablesId(),
                highVoltCablesRepositoryById.getHighVoltCablesId());
        Assertions.assertEquals(highVoltCablesResponseDTO.getHighVoltCablesList().get(0).getCableType(),
                highVoltCablesRepositoryById.getCableType());


    }
    @Test
    @Sql(scripts = {"/sql/clearHighVoltInformationDB.sql","/sql/clearHighVoltCablesSelectionDB.sql",
            "/sql/clearInductiveImpedanceAreasDB.sql","/sql/addHighVoltCablesSelectionInformation.sql",
            "/sql/clearHighVoltInformationInductiveImpedanceAreasDB.sql"})
    public void createHighVoltCablesSelectionInformation() throws Exception {

        Mockito.when(powerTransformersServiceClient.getPowerTransformersInformationById(ArgumentMatchers.anyShort()))
                .thenReturn(createPowerTransformerByIdResponseDTO());

        String REQUEST = createHighVoltInformationRequestAsString();

        MvcResult mvcResult = mockMvc.perform(put("/create/selectionInformation")
                        .content(REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        HighVoltCablesSelectionInformationResponseDTO highVoltCablesSelectionInformationResponseDTO = objectMapper
                .readValue(body, HighVoltCablesSelectionInformationResponseDTO.class);

        HighVoltCablesSelection highVoltCablesSelectionRepositoryById = highVoltCablesSelectionRepository.findById((short) 3)
                .orElseThrow(() -> new NoSuchElementException("No value present"));

        HighVoltInformation highVoltInformationRepositoryById = highVoltInformationRepository.findById((short) 3)
                .orElseThrow(() -> new NoSuchElementException("No value present"));
        List<HighVoltCablesSelection> highVoltCablesSelectionRepositoryAll = highVoltCablesSelectionRepository.findAll();

        List<HighVoltInformationInductiveImpedanceAreas> allByHighVoltInformationIdFk = highVoltInformationInductiveImpedanceAreasRepository.
                findByHighVoltInformationIdFk((short) 3);
        List<Short> inductiveImpedanceAreasIds = new ArrayList<>();
        for (HighVoltInformationInductiveImpedanceAreas s : allByHighVoltInformationIdFk) {
            inductiveImpedanceAreasIds.add(s.getInductiveImpedanceAreasIdFk());
        }
        List<InductiveImpedanceAreas> byInductiveImpedanceAreasId = inductiveImpedanceAreasRepository
                .findByInductiveImpedanceAreasIdIn(inductiveImpedanceAreasIds);

        Assertions.assertEquals(highVoltCablesSelectionRepositoryById.getMinCableSectionForSelect(),
                highVoltCablesSelectionInformationResponseDTO.getHighVoltCablesSelectionList().get(0).getMinCableSectionForSelect());


        Assertions.assertEquals(3, highVoltInformationRepositoryById.getHighVoltInformationId());
        Assertions.assertEquals(0.08F, highVoltInformationRepositoryById.getHighVoltageAirLineInductiveResistance());
        Assertions.assertEquals(0.08F, highVoltInformationRepositoryById.getHighVoltageCableLineInductiveResistance());
        Assertions.assertEquals(0.18F, highVoltInformationRepositoryById.getHighVoltageCableLineActiveResistance());
        Assertions.assertEquals(1.8F, highVoltInformationRepositoryById.getSurgeCoefficient());
        Assertions.assertEquals(1.2F, highVoltInformationRepositoryById.getEconomicCurrentDensity());
        Assertions.assertEquals(0.5F, highVoltInformationRepositoryById.getShortCircuitTime());
        Assertions.assertEquals(85.0F, highVoltInformationRepositoryById.getCoefficientTakingEmittedHeatDifference());
        Assertions.assertEquals(400.0F, highVoltInformationRepositoryById.getProductionHallTransformerFullPower());
        Assertions.assertEquals(115, highVoltInformationRepositoryById.getBaseVoltage());
        Assertions.assertEquals(300, highVoltInformationRepositoryById.getBaseFullPower());
        Assertions.assertEquals(0.5F, highVoltInformationRepositoryById.getRelativeBaselineUnrestrictedPowerResistance());
        Assertions.assertEquals(40.0F, highVoltInformationRepositoryById.getHighVoltageAirLineLength());
        Assertions.assertEquals(160.0F, highVoltInformationRepositoryById.getHeadTransformerFullPower());
        Assertions.assertEquals(10.5F, highVoltInformationRepositoryById.getShortCircuitVoltage());
        Assertions.assertEquals(2.5F, highVoltInformationRepositoryById.getCableLineLength());
        Assertions.assertEquals(10.0F, highVoltInformationRepositoryById.getRatedVoltageOfHigherVoltageWindingOfTransformer());
        Assertions.assertEquals(0.5F, highVoltInformationRepositoryById.getRelativeBasisResistance());
        Assertions.assertEquals(0.07F, highVoltInformationRepositoryById.getPowerLineRelativeResistance());
        Assertions.assertEquals(0.2F, highVoltInformationRepositoryById.getFirstTransformerRelativeReactiveResistance());
        Assertions.assertEquals(0.54F, highVoltInformationRepositoryById.getCableLineRelativeReactiveResistance());
        Assertions.assertEquals(41.25F, highVoltInformationRepositoryById.getSecondTransformerRelativeReactiveResistance());
        Assertions.assertEquals(1.31F, highVoltInformationRepositoryById.getReactiveResistanceAtPointK1());
        Assertions.assertEquals(16.5F, highVoltInformationRepositoryById.getBaseCurrentAtPointK1());
        Assertions.assertEquals(1.88F, highVoltInformationRepositoryById.getFullResistanceAtPointK1());
        Assertions.assertEquals(8.78F, highVoltInformationRepositoryById.getShortCircuitCurrentAtPointK1());
        Assertions.assertEquals(22.35F, highVoltInformationRepositoryById.getSurgeCurrentAtPointK1());
        Assertions.assertEquals(15.96F, highVoltInformationRepositoryById.getShortCircuitPowerAtPointK1());
        Assertions.assertEquals(23.09F, highVoltInformationRepositoryById.getRatedPowerTransformerCurrent());
        Assertions.assertEquals(0.5F, byInductiveImpedanceAreasId.get(0).getInductiveResistance());
        Assertions.assertEquals(0.07F, byInductiveImpedanceAreasId.get(1).getInductiveResistance());
        Assertions.assertEquals(0.2F, byInductiveImpedanceAreasId.get(2).getInductiveResistance());
        Assertions.assertEquals(0.54F, byInductiveImpedanceAreasId.get(3).getInductiveResistance());
        Assertions.assertEquals(73.04F, highVoltCablesSelectionRepositoryAll.get(0).getMinCableSectionForSelect());
    }

    @Test
    @Sql(scripts = { "/sql/clearHighVoltInformationDB.sql", "/sql/clearHighVoltCablesDB.sql","/sql/clearHighVoltCablesSelectionDB.sql",
            "/sql/addHighVoltInformation.sql", "/sql/addHighVoltCablesInformation.sql", "/sql/addHighVoltCablesSelectionInformation.sql"})
    public void updateHighVoltCable() throws Exception {

        String REQUEST = createHighVoltCablleUpdateRequestAsString();

        MvcResult mvcResult = mockMvc.perform(put("/update/highVoltCable")
                        .content(REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        HighVoltCablesResponseDTO highVoltCablesResponseDTO = objectMapper
                .readValue(body, HighVoltCablesResponseDTO.class);

        HighVoltCables highVoltCablesRepositoryById = highVoltCablesRepository.findById((short) 3)
                .orElseThrow(() -> new NoSuchElementException("No value present"));

        Assertions.assertEquals(highVoltCablesResponseDTO.getHighVoltCablesList().get(0).getHighVoltCablesId(),
                highVoltCablesRepositoryById.getHighVoltCablesId());
        Assertions.assertEquals(highVoltCablesResponseDTO.getHighVoltCablesList().get(0).getCableType(),
                highVoltCablesRepositoryById.getCableType());

    }

    @Test
    @Sql(scripts = { "/sql/clearHighVoltInformationDB.sql", "/sql/clearInductiveImpedanceAreasDB.sql",
            "/sql/clearHighVoltCablesSelectionDB.sql", "/sql/clearHighVoltInformationInductiveImpedanceAreasDB.sql",
            "/sql/addHighVoltInformation.sql", "/sql/addHighVoltCablesSelectionInformation.sql",
            "/sql/addHighVoltInformationInductiveImpedanceAreasInformation.sql","/sql/addInductiveImpedanceAreasInformation.sql"})
    public void updateHighVoltCablesSelectionInformation() throws Exception {

        Mockito.when(powerTransformersServiceClient.getPowerTransformersInformationById(ArgumentMatchers.anyShort()))
                .thenReturn(createPowerTransformerByIdResponseDTO());

        String REQUEST = createHighVoltInformationUpdateRequestAsString();

        MvcResult mvcResult = mockMvc.perform(put("/update/selectionInformation")
                        .content(REQUEST)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        HighVoltCablesSelectionInformationResponseDTO highVoltCablesSelectionInformationResponseDTO = objectMapper
                .readValue(body, HighVoltCablesSelectionInformationResponseDTO.class);

        HighVoltCablesSelection highVoltCablesSelectionRepositoryById = highVoltCablesSelectionRepository.findById((short) 3)
                .orElseThrow(() -> new NoSuchElementException("No value present"));

        HighVoltInformation highVoltInformationRepositoryById = highVoltInformationRepository.findById((short) 3)
                .orElseThrow(() -> new NoSuchElementException("No value present"));

        List<HighVoltCablesSelection> highVoltCablesSelectionRepositoryAll = highVoltCablesSelectionRepository.findAll();

        List<HighVoltInformationInductiveImpedanceAreas> allByHighVoltInformationIdFk = highVoltInformationInductiveImpedanceAreasRepository.
                findByHighVoltInformationIdFk((short) 3);

        List<Short> inductiveImpedanceAreasIds = new ArrayList<>();
        for (HighVoltInformationInductiveImpedanceAreas s : allByHighVoltInformationIdFk) {
            inductiveImpedanceAreasIds.add(s.getInductiveImpedanceAreasIdFk());
        }
        List<InductiveImpedanceAreas> byInductiveImpedanceAreasId = inductiveImpedanceAreasRepository
                .findByInductiveImpedanceAreasIdIn(inductiveImpedanceAreasIds);
        Assertions.assertEquals(highVoltCablesSelectionRepositoryById.getMinCableSectionForSelect(),
                highVoltCablesSelectionInformationResponseDTO.getHighVoltCablesSelectionList().get(0).getMinCableSectionForSelect());


        Assertions.assertEquals(3, highVoltInformationRepositoryById.getHighVoltInformationId());
        Assertions.assertEquals(0.08F, highVoltInformationRepositoryById.getHighVoltageAirLineInductiveResistance());
        Assertions.assertEquals(0.08F, highVoltInformationRepositoryById.getHighVoltageCableLineInductiveResistance());
        Assertions.assertEquals(0.18F, highVoltInformationRepositoryById.getHighVoltageCableLineActiveResistance());
        Assertions.assertEquals(1.8F, highVoltInformationRepositoryById.getSurgeCoefficient());
        Assertions.assertEquals(1.2F, highVoltInformationRepositoryById.getEconomicCurrentDensity());
        Assertions.assertEquals(0.5F, highVoltInformationRepositoryById.getShortCircuitTime());
        Assertions.assertEquals(85.0F, highVoltInformationRepositoryById.getCoefficientTakingEmittedHeatDifference());
        Assertions.assertEquals(400.0F, highVoltInformationRepositoryById.getProductionHallTransformerFullPower());
        Assertions.assertEquals(115, highVoltInformationRepositoryById.getBaseVoltage());
        Assertions.assertEquals(300, highVoltInformationRepositoryById.getBaseFullPower());
        Assertions.assertEquals(0.7F, highVoltInformationRepositoryById.getRelativeBaselineUnrestrictedPowerResistance());
        Assertions.assertEquals(60.0F, highVoltInformationRepositoryById.getHighVoltageAirLineLength());
        Assertions.assertEquals(180.0F, highVoltInformationRepositoryById.getHeadTransformerFullPower());
        Assertions.assertEquals(10.5F, highVoltInformationRepositoryById.getShortCircuitVoltage());
        Assertions.assertEquals(5.0F, highVoltInformationRepositoryById.getCableLineLength());
        Assertions.assertEquals(6.0F, highVoltInformationRepositoryById.getRatedVoltageOfHigherVoltageWindingOfTransformer());
        Assertions.assertEquals(0.7F, highVoltInformationRepositoryById.getRelativeBasisResistance());
        Assertions.assertEquals(0.11F, highVoltInformationRepositoryById.getPowerLineRelativeResistance());
        Assertions.assertEquals(0.18F, highVoltInformationRepositoryById.getFirstTransformerRelativeReactiveResistance());
        Assertions.assertEquals(1.09F, highVoltInformationRepositoryById.getCableLineRelativeReactiveResistance());
        Assertions.assertEquals(41.25F, highVoltInformationRepositoryById.getSecondTransformerRelativeReactiveResistance());
        Assertions.assertEquals(1.9F, highVoltInformationRepositoryById.getReactiveResistanceAtPointK1());
        Assertions.assertEquals(16.5F, highVoltInformationRepositoryById.getBaseCurrentAtPointK1());
        Assertions.assertEquals(7.74F, highVoltInformationRepositoryById.getFullResistanceAtPointK1());
        Assertions.assertEquals(2.13F, highVoltInformationRepositoryById.getShortCircuitCurrentAtPointK1());
        Assertions.assertEquals(5.42F, highVoltInformationRepositoryById.getSurgeCurrentAtPointK1());
        Assertions.assertEquals(3.88F, highVoltInformationRepositoryById.getShortCircuitPowerAtPointK1());
        Assertions.assertEquals(38.49F, highVoltInformationRepositoryById.getRatedPowerTransformerCurrent());
        Assertions.assertEquals(0.7F, byInductiveImpedanceAreasId.get(0).getInductiveResistance());
        Assertions.assertEquals(0.09F, byInductiveImpedanceAreasId.get(1).getInductiveResistance());
        Assertions.assertEquals(0.26F, byInductiveImpedanceAreasId.get(2).getInductiveResistance());
        Assertions.assertEquals(0.85F, byInductiveImpedanceAreasId.get(3).getInductiveResistance());
        Assertions.assertEquals(32.08F, highVoltCablesSelectionRepositoryAll.get(0).getMinCableSectionForSelect());
    }


    @Test
    @Sql(scripts = {"/sql/clearHighVoltInformationDB.sql", "/sql/clearInductiveImpedanceAreasDB.sql",
            "/sql/clearHighVoltCablesSelectionDB.sql", "/sql/clearHighVoltInformationInductiveImpedanceAreasDB.sql",
            "/sql/addHighVoltInformation.sql", "/sql/addHighVoltCablesSelectionInformation.sql",
            "/sql/addHighVoltInformationInductiveImpedanceAreasInformation.sql","/sql/addInductiveImpedanceAreasInformation.sql"})
    public void deleteHighVoltSelectionInformationById() throws Exception {

        MvcResult mvcResult = mockMvc.perform(delete("/delete/selectionInformation/3"))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        HighVoltInformationResponseDTO highVoltInformationResponseDTO = objectMapper
                .readValue(body, HighVoltInformationResponseDTO.class);
        List<HighVoltCablesSelection> highVoltCableSelectionResponseList = highVoltInformationResponseDTO.getHighVoltCableSelectionList();
        List<HighVoltInformation> highVoltInformationResponseList = highVoltInformationResponseDTO.getHighVoltInformationList();

        List<HighVoltCablesSelection> highVoltCablesSelectionRepositoryAll = highVoltCablesSelectionRepository.findAll();
        List<HighVoltInformation> highVoltInformationRepositoryAll = highVoltInformationRepository.findAll();

        Assertions.assertEquals(highVoltCableSelectionResponseList.get(0).getHighVoltCableSelectionId(),
                highVoltCablesSelectionRepositoryAll.get(0).getHighVoltCableSelectionId(), 6);

        Assertions.assertEquals(highVoltInformationResponseList.get(0).getHighVoltInformationId(),
                highVoltInformationRepositoryAll.get(0).getHighVoltInformationId(), 6);

    }


    @Test
    @Sql(scripts = { "/sql/clearHighVoltCablesDB.sql", "/sql/addHighVoltCablesInformation.sql"})
    public void deleteCableById() throws Exception {

        MvcResult mvcResult = mockMvc.perform(delete("/delete/3"))
                .andExpect(status()
                        .isOk())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        HighVoltCablesResponseDTO highVoltCablesResponseDTO = objectMapper
                .readValue(body, HighVoltCablesResponseDTO.class);

        List<HighVoltCables> highVoltCablesRepositoryAll = highVoltCablesRepository.findAll();

        Assertions.assertEquals(highVoltCablesResponseDTO.getHighVoltCablesList().get(0).getHighVoltCablesId(),
                highVoltCablesRepositoryAll.get(0).getHighVoltCablesId(), 6);

    }


    private String createHighVoltInformationRequestAsString() throws JsonProcessingException {
        List<Float> inductiveResistanceList = new ArrayList<>();
        inductiveResistanceList.add(0.5F);
        inductiveResistanceList.add(0.07F);
        inductiveResistanceList.add(0.2F);
        inductiveResistanceList.add(0.54F);
        HighVoltInformationRequestDTO requestDTO = new HighVoltInformationRequestDTO((short) 3, (short) 115, (short) 300,
                0.5F, 40F, 160F,
                10.5F, 2.5F, 10, inductiveResistanceList);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(requestDTO);
    }

    private String createHighVoltInformationUpdateRequestAsString() throws JsonProcessingException {
        List<Float> inductiveResistanceList = new ArrayList<>();
        inductiveResistanceList.add(0.7F);
        inductiveResistanceList.add(0.09F);
        inductiveResistanceList.add(0.26F);
        inductiveResistanceList.add(0.85F);
        HighVoltInformationRequestDTO requestDTO = new HighVoltInformationRequestDTO((short) 3, (short) 115, (short) 300,
                0.7F, 60F, 180F,
                10.5F, 5.0F, 6, inductiveResistanceList);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(requestDTO);
    }

    private String createHighVoltCablleRequestAsString() throws JsonProcessingException {

        HighVoltCablesSelectionInformationRequestDTO highVoltCablesSelectionInformationRequestDTO =
                new HighVoltCablesSelectionInformationRequestDTO((short)3, "ASBl 3x75");

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(highVoltCablesSelectionInformationRequestDTO);
    }

    private String createHighVoltCablleUpdateRequestAsString() throws JsonProcessingException {

        HighVoltCablesSelectionInformationRequestDTO highVoltCablesSelectionInformationRequestDTO =
                new HighVoltCablesSelectionInformationRequestDTO((short)3, "ASBl 3x120");

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(highVoltCablesSelectionInformationRequestDTO);
    }

    private PowerTransformerByIdResponseDTO createPowerTransformerByIdResponseDTO() {
        PowerTransformerByIdResponseDTO powerTransformerByIdResponseDTO = new PowerTransformerByIdResponseDTO();
        PowerTransformers powerTransformers = new PowerTransformers((short) 3, "TMG-400",
                400.0F, 0.1F, 5.5F, 2000.0F,
                10.0F, 0.4F, 10.5F, 1.0F);

        powerTransformerByIdResponseDTO.setPowerTransformers(powerTransformers);

        return powerTransformerByIdResponseDTO;

    }

}
