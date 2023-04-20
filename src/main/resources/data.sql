INSERT INTO event(created_at, update_time, content, end_date, fee, hashtag, people_limit, recruit_deadline, start_date, status, summary, title, title_tag)
VALUES
    (NOW(), NOW(), 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed aliquam eros ut ante eleifend, in blandit arcu vehicula.', '2023-05-01 18:00:00', 5000, '#event #sample', 50, '2023-04-30 23:59:59', '2023-05-01 14:00:00', 'OPEN', 'A sample event description.', 'Sample Event 1', 'sample-event-1'),
    (NOW(), NOW(), 'Phasellus tempor suscipit aliquam. Ut vel arcu tellus.', '2023-06-01 17:00:00', 0, '#event #sample', 30, '2023-05-31 23:59:59', '2023-06-01 10:00:00', 'CLOSE', 'Another sample event description.', 'Sample Event 2', 'sample-event-2'),
    (NOW(), NOW(), 'Donec ut neque magna. Suspendisse malesuada enim arcu, vitae consequat mi blandit sit amet.', '2023-07-01 16:00:00', 10000, '#event #sample', 20, '2023-06-30 23:59:59', '2023-07-01 12:00:00', 'OPEN', 'Yet another sample event description.', 'Sample Event 3', 'sample-event-3');

SET @cnt = 0;
WHILE @cnt < 100 DO
    INSERT INTO event (created_at, update_time, content, end_date, fee, hashtag, people_limit, recruit_deadline, start_date, status, summary, title, title_tag)
    VALUES (NOW(), NOW(), CONCAT('Event content ', @cnt), DATE_ADD(NOW(), INTERVAL @cnt DAY), @cnt, CONCAT('#hashtag', @cnt), @cnt, DATE_ADD(NOW(), INTERVAL @cnt-1 DAY), DATE_ADD(NOW(), INTERVAL @cnt DAY), 'OPEN', CONCAT('Event summary ', @cnt), CONCAT('Event title ', @cnt), CONCAT('tag', @cnt));
    SET @cnt = @cnt + 1;
END WHILE;