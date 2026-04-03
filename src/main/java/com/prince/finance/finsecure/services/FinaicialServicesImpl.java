package com.prince.finance.finsecure.services;

import com.prince.finance.finsecure.DTO.FinancialRecordDto;
import com.prince.finance.finsecure.DTO.FinancialRecordResponseDto;

import com.prince.finance.finsecure.DTO.UserFinancialRecordDto;
import com.prince.finance.finsecure.DTO.UserResponseDto;
import com.prince.finance.finsecure.entities.Financial_record;
import com.prince.finance.finsecure.entities.User;
import com.prince.finance.finsecure.exceptions.CommonExceptions;
import com.prince.finance.finsecure.repository.FinancialRecordRepository;
import com.prince.finance.finsecure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class FinaicialServicesImpl implements FinancialServices {

    private final UserRepository userRepository;
    private final FinancialRecordRepository financialRecordRepository;

    // ─── Entity → ResponseDto ───────────────────────────────
    private FinancialRecordResponseDto mapToDto(Financial_record record) {
        FinancialRecordResponseDto dto = new FinancialRecordResponseDto();
        dto.setFid(record.getFid());
        dto.setAmount(record.getAmount());
        dto.setType(record.getType());
        dto.setCategory(record.getCategory());
        dto.setDate(record.getDate());
        dto.setNote(record.getNote());
        dto.setUserId(record.getUser().getId());
        dto.setUsername(record.getUser().getUsername());
        return dto;
    }

    // ─── CREATE ─────────────────────────────────────────────
    @Override
    public FinancialRecordResponseDto createRecord(FinancialRecordDto dto) {
        log.info("Creating record for user ID: {}", dto.getUserId());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CommonExceptions(
                        "User not found with id: " + dto.getUserId()));

        Financial_record record = new Financial_record();
        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setNote(dto.getNote());
        record.setUser(user);

        Financial_record saved = financialRecordRepository.save(record);
        log.info("Record created with ID: {}", saved.getFid());
        return mapToDto(saved);
    }

    // ─── GET BY ID ───────────────────────────────────────────
    @Transactional(readOnly = true)
    @Override
    public FinancialRecordResponseDto findRecordById(Long id) {
        log.info("Fetching record with ID: {}", id);
        Financial_record record = financialRecordRepository.findById(id)
                .orElseThrow(() -> new CommonExceptions(
                        "Record not found with id: " + id));
        return mapToDto(record);
    }


    @Transactional(readOnly = true)
    @Override
    public List<UserFinancialRecordDto> getAllRecords() {
        log.info("Fetching all records grouped by user");

        List<User> users = userRepository.findAll();
        List<UserFinancialRecordDto> result = new ArrayList<>();

        for (User user : users) {
            UserFinancialRecordDto userFinancialRecordDto = new UserFinancialRecordDto();
            userFinancialRecordDto.setUserId(user.getId());
            userFinancialRecordDto.setUsername(user.getUsername());
            userFinancialRecordDto.setEmail(user.getEmail());

            List<Financial_record> records =
                    financialRecordRepository.findByUserId(user.getId());
            List<FinancialRecordResponseDto> recordDos = new ArrayList<>();

            for (Financial_record record : records) {
                recordDos.add(mapToDto(record));
            }

            userFinancialRecordDto.setRecords(recordDos);
            result.add(userFinancialRecordDto);
        }
        return result;
    }

}