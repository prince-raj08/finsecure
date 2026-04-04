package com.prince.finance.finsecure.services;

import com.prince.finance.finsecure.DTO.FinancialRecordDto;
import com.prince.finance.finsecure.DTO.FinancialRecordResponseDto;
import com.prince.finance.finsecure.DTO.UserFinancialRecordDto;
import com.prince.finance.finsecure.entities.Financial_record;
import com.prince.finance.finsecure.entities.User;
import com.prince.finance.finsecure.enums.Category;
import com.prince.finance.finsecure.enums.Type;
import com.prince.finance.finsecure.exceptions.CommonExceptions;
import com.prince.finance.finsecure.repository.FinancialRecordRepository;
import com.prince.finance.finsecure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class FinancialServicesImpl implements FinancialServices {

    private final UserRepository userRepository;
    private final FinancialRecordRepository financialRecordRepository;

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
            UserFinancialRecordDto userFinancialRecordDto =
                    new UserFinancialRecordDto();
            userFinancialRecordDto.setUserId(user.getId());
            userFinancialRecordDto.setUsername(user.getUsername());
            userFinancialRecordDto.setEmail(user.getEmail());

            List<Financial_record> records =
                    financialRecordRepository
                            .findByUserIdAndDeletedFalse(user.getId());
            List<FinancialRecordResponseDto> recordDtos = new ArrayList<>();

            for (Financial_record record : records) {
                recordDtos.add(mapToDto(record));
            }

            userFinancialRecordDto.setRecords(recordDtos);
            result.add(userFinancialRecordDto);
        }
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public List<FinancialRecordResponseDto> getRecordsByUserId(Long userId) {
        log.info("Fetching records for user ID: {}", userId);

        userRepository.findById(userId)
                .orElseThrow(() -> new CommonExceptions(
                        "User not found with id: " + userId));

        List<Financial_record> records =
                financialRecordRepository.findByUserIdAndDeletedFalse(userId);
        List<FinancialRecordResponseDto> result = new ArrayList<>();

        for (Financial_record record : records) {
            result.add(mapToDto(record));
        }
        return result;
    }


    @Override
    public String updateRecord(Long id, FinancialRecordDto dto) {
        log.info("Updating record with ID: {}", id);

        Financial_record record = financialRecordRepository.findById(id)
                .orElseThrow(() -> new CommonExceptions(
                        "Record not found with id: " + id));

        record.setAmount(dto.getAmount());
        record.setType(dto.getType());
        record.setCategory(dto.getCategory());
        record.setDate(dto.getDate());
        record.setNote(dto.getNote());

        financialRecordRepository.save(record);
        log.info("Record updated with ID: {}", id);
        return "Record updated successfully";
    }


    @Override
    public String deleteRecord(Long id) {
        log.info("Soft deleting record with ID: {}", id);

        Financial_record record = financialRecordRepository.findById(id)
                .orElseThrow(() -> new CommonExceptions(
                        "Record not found with id: " + id));

        record.setDeleted(true);
        financialRecordRepository.save(record);
        log.info("Record soft deleted with ID: {}", id);
        return "Record deleted successfully";
    }


    @Transactional(readOnly = true)
    @Override
    public List<FinancialRecordResponseDto> getRecordsByType(Type type) {
        log.info("Fetching records by type: {}", type);

        List<Financial_record> records =
                financialRecordRepository.findByTypeAndDeletedFalse(type);
        List<FinancialRecordResponseDto> result = new ArrayList<>();

        for (Financial_record record : records) {
            result.add(mapToDto(record));
        }
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public List<FinancialRecordResponseDto> getRecordsByCategory(
            Category category) {
        log.info("Fetching records by category: {}", category);

        List<Financial_record> records =
                financialRecordRepository
                        .findByCategoryAndDeletedFalse(category);
        List<FinancialRecordResponseDto> result = new ArrayList<>();

        for (Financial_record record : records) {
            result.add(mapToDto(record));
        }
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public List<FinancialRecordResponseDto> getRecordsByDateRange(
            LocalDateTime start, LocalDateTime end) {
        log.info("Fetching records from {} to {}", start, end);

        List<Financial_record> records =
                financialRecordRepository
                        .findByDateBetweenAndDeletedFalse(start, end);
        List<FinancialRecordResponseDto> result = new ArrayList<>();

        for (Financial_record record : records) {
            result.add(mapToDto(record));
        }
        return result;
    }
}