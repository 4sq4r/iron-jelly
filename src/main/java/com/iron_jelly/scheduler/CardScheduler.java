package com.iron_jelly.scheduler;

import com.iron_jelly.model.entity.Card;
import com.iron_jelly.repository.CardRepository;
import com.iron_jelly.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CardScheduler {
    private final CardRepository cardRepository;
    private final CardService cardService;

    @Scheduled(cron = "0 0 0 * * ?") // Запуск каждый день в полночь
    public void deactivateExpiredCards() {
        List<Card> expiredCards = cardRepository.findAllByExpireDateBeforeAndActive(LocalDate.now(), true);
        for (Card card : expiredCards) {
            cardService.deactivateCard(card);
            cardRepository.save(card);
        }
    }
}
