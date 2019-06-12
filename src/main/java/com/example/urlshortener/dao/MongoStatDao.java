package com.example.urlshortener.dao;

import com.example.urlshortener.model.Stat;
import com.example.urlshortener.model.Url;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Repository("MongoStatDao")
public class MongoStatDao implements StatDao {

    private final MongoTemplate mongoTemplate;
    private final MongoStatRepository mongoStatRepository;

    public MongoStatDao(MongoTemplate mongoTemplate, MongoStatRepository mongoStatRepository) {
        this.mongoTemplate = mongoTemplate;
        this.mongoStatRepository = mongoStatRepository;
    }

    @Override
    public boolean insertStat(Stat stat) {
        try {
            mongoStatRepository.save(stat);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Object getStats(Url url) {
        String originalUrlString = url.getUrl();
        int total = mongoStatRepository.findByUrlId(url.getId()).size();
        int week = getStatsForLastXDays(url.getId(), 7);
        int month = getStatsForLastXDays(url.getId(), 30);

        return new Object() {
            public final String originalUrl = originalUrlString;
            public final int totalClicks = total;
            public final int last7Days = week;
            public final int last30Days = month;
        };
    }

    private int getStatsForLastXDays(long id, int days) {
        LocalDateTime now = LocalDateTime.now().minusDays(days);
        Date convertedDate = java.util.Date.from(now.atZone(ZoneId.systemDefault()).toInstant());

        ObjectId idSince = new ObjectId(convertedDate);
        List<Stat> last7Days = mongoStatRepository.findByUrlIdAndDate(idSince, id);

        return last7Days.size();
    }
}
