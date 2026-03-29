package ClothesShop.spring_restapi_clothesshop.config;

import ClothesShop.spring_restapi_clothesshop.repository.RefreshTokenRepository;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private final RefreshTokenRepository refreshTokenRepository;

    public ScheduledTasks(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void cleanupExpiredRefreshTokens() {
        int deletedCount = refreshTokenRepository.deleteAllExpiredBefore(Instant.now());
        log.info("Deleted {} expired refresh tokens", deletedCount);
    }
}
