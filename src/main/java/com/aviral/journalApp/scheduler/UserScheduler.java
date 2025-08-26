package com.aviral.journalApp.scheduler;

import com.aviral.journalApp.entity.Journal;
import com.aviral.journalApp.entity.User;
import com.aviral.journalApp.enums.Sentiment;
import com.aviral.journalApp.repository.UserRepositoryImpl;
import com.aviral.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

//    @Scheduled(cron = "0 0 9 ? * SUN")
//    @Scheduled(cron = "0 * * * * *")
    @Scheduled(cron = "0 35 19 ? * TUE,THU")
    public void fetchUsersAndSendSentimentAnalysisMail() {
        List<User> usersForSentimentAnalysis = userRepository.getUsersForSentimentAnalysis();

        for (User user : usersForSentimentAnalysis) {
            List<Journal> journals = user.getJournals();

            List<Sentiment> sentiments = journals.stream()
                    .filter(x -> x.getDate().isAfter(LocalDateTime.now().minusDays(7)))
                    .map(Journal::getSentiment)
                    .toList();

            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();

            for (Sentiment sentiment : sentiments) {
                if (sentiment != null)
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;

            for (Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if (mostFrequentSentiment != null)
                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 Days!",
                        "As per the journals for last 7 days your sentiment is: " + mostFrequentSentiment);
        }
    }
}
