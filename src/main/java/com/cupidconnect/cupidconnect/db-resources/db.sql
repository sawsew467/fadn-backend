DROP DATABASE IF EXISTS cupid_connect;
CREATE DATABASE cupid_connect;
USE cupid_connect;
-- CREATE TABLE SQL 
CREATE TABLE `profiles` (
                            `id` INT AUTO_INCREMENT PRIMARY KEY,
                            `avatar` NVARCHAR(50) NULL,
                            `intro` NVARCHAR(255) NULL,
                            `hobbies` NVARCHAR(255) NULL,
                            `work_at` NVARCHAR(100) NULL,
                            KEY `Key` (`avatar`, `intro`, `hobbies`, `work_at`)
);
CREATE TABLE `genders` (
                           `id` INT AUTO_INCREMENT PRIMARY KEY,
                           `name` NVARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE `feedbacks` (
                             `id` INT AUTO_INCREMENT PRIMARY KEY,
                             `description` TEXT,
                             `rate` INT NOT NULL CHECK (`rate` BETWEEN 1 AND 5)
);
CREATE TABLE `roles` (
                         `id` INT AUTO_INCREMENT PRIMARY KEY,
                         `name` NVARCHAR(50) NOT NULL UNIQUE
);
CREATE TABLE `users` (
                         `id` INT AUTO_INCREMENT PRIMARY KEY,
                         `first_name` NVARCHAR(50) NOT NULL,
                         `last_name` NVARCHAR(50) NOT NULL,
                         `nickname` NVARCHAR(100) UNIQUE,
                         `password` VARCHAR(255) NOT NULL,
                         `gender_id` INT,
                         `email` VARCHAR(50) NOT NULL UNIQUE,
                         `phone` CHAR(15) UNIQUE,
                         `address` NVARCHAR(200),
                         `date_of_birth` DATE,
                         `fb_account_id` INT UNIQUE,
                         `google_account_id` INT UNIQUE,
                         `confirmation_code` VARCHAR(255) UNIQUE,
                         `confirmation_time` DATETIME,
                         `popularity` DECIMAL(5,2) DEFAULT 0,
                         `is_active` TINYINT(1) NOT NULL DEFAULT 1 CHECK (is_active IN (0,1)),
                         `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                         `is_admin` TINYINT(1) NOT NULL DEFAULT 0 CHECK (is_admin IN (0,1)),
                         `role_id` INT,
                         `profile_id` INT,
                         FOREIGN KEY (`role_id`) REFERENCES `roles`(`id`),
                         FOREIGN KEY (`gender_id`) REFERENCES `genders`(`id`),
                         FOREIGN KEY (`profile_id`) REFERENCES `profiles`(`id`),
                         KEY `Key` (`first_name`, `last_name`, `nickname`, `email`, `phone`)
);
CREATE TRIGGER before_insert_users
    BEFORE INSERT ON users
    FOR EACH ROW
BEGIN
    IF NEW.date_of_birth > CURDATE() THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'The date of birth cannot be in the future.';
    END IF;
END;
CREATE TABLE `social_accounts` (
                                   `id` INT AUTO_INCREMENT PRIMARY KEY,
                                   `provider` VARCHAR(20) NOT NULL,
                                   `provider_id` VARCHAR(50) NOT NULL,
                                   `email` VARCHAR(150) NOT NULL UNIQUE,
                                   `name` VARCHAR(100),
                                   `user_id` INT,
                                   FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
                                   KEY `Key` (`provider`, `email`, `name`)
);
CREATE TABLE `conversations` (
                                 `id` INT AUTO_INCREMENT PRIMARY KEY,
                                 `participant_1` INT NOT NULL,
                                 `participant_2` INT NOT NULL,                                 `time_started` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                 `time_closed` TIMESTAMP NULL DEFAULT NULL,
                                 status VARCHAR(50),
                                 `feedback_id` INT,
                                 FOREIGN KEY (`participant_1`) REFERENCES `users`(`id`),
                                 FOREIGN KEY (`participant_2`) REFERENCES `users`(`id`),
                                 FOREIGN KEY (`feedback_id`) REFERENCES `feedbacks`(`id`)
);


CREATE TABLE `relationship_type` (
                                     `id` INT AUTO_INCREMENT PRIMARY KEY,
                                     `name` NVARCHAR(50)
);
ALTER TABLE `relationship_type`
    ADD CONSTRAINT `unique_name` UNIQUE (`name`);

CREATE TABLE notifications (
                               id INT AUTO_INCREMENT PRIMARY KEY,
                               sender_id INT NOT NULL, -- nguoi gui
                               recipient_id INT NOT NULL, -- nguoi nhan
                               message TEXT NOT NULL, -- noi dung
                               timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, -- thoi gian gui
                               read_status BOOLEAN DEFAULT FALSE, -- trang thai doc
                               FOREIGN KEY (sender_id) REFERENCES users(id),
                               FOREIGN KEY (recipient_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `grades` (
                          `id` INT AUTO_INCREMENT PRIMARY KEY,
                          `user_id` INT NOT NULL,
                          `user_id_received` INT NOT NULL,
                          `grade` INT NOT NULL CHECK (`grade` BETWEEN 1 AND 10),
                          FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
                          FOREIGN KEY (`user_id_received`) REFERENCES `users`(`id`),
                          KEY `Key` (`grade`)
);
ALTER TABLE `grades`
    ADD CONSTRAINT `uc_user_user_received` UNIQUE (`user_id`, `user_id_received`);
CREATE TABLE `interested_in_relation` (
                                          `id` INT AUTO_INCREMENT PRIMARY KEY,
                                          `user_id` INT,
                                          `relationship_type_id` INT,
                                          FOREIGN KEY (`relationship_type_id`) REFERENCES `relationship_type`(`id`)
);
ALTER TABLE `interested_in_relation`
    MODIFY `user_id` INT NOT NULL,
    MODIFY `relationship_type_id` INT NOT NULL,
    ADD CONSTRAINT `uc_user_relationship_type` UNIQUE (`user_id`, `relationship_type_id`);
CREATE TABLE `user_photos` (
                               `id` INT AUTO_INCREMENT PRIMARY KEY,
                               `user_id` INT,
                               `link` TEXT NOT NULL,
                               `details` TEXT,
                               `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               `is_active` TINYINT(1) NOT NULL,
                               FOREIGN KEY (`user_id`) REFERENCES `users`(`id`)
);

ALTER TABLE `user_photos` ADD KEY `UserPhotoIndex` (`created_at`, `is_active`);


CREATE TABLE `messages` (
                            `id` INT AUTO_INCREMENT PRIMARY KEY,
                            `from_user_id` INT NOT NULL,
                            `to_user_id` INT NOT NULL,
                            `message_text` TEXT,
                            `send_datetime` DATETIME NOT NULL,
                            FOREIGN KEY (`from_user_id`) REFERENCES `users`(`id`),
                            FOREIGN KEY (`to_user_id`) REFERENCES `users`(`id`),
                            KEY `Key` (`send_datetime`)
);
CREATE TABLE `blocks` (
                          `id` INT AUTO_INCREMENT PRIMARY KEY,
                          `user_id` INT NOT NULL,
                          `user_id_blocked` INT NOT NULL,
                          FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
                          FOREIGN KEY (`user_id_blocked`) REFERENCES `users`(`id`)
);

CREATE TABLE `interest_genders` (
                                    `id` INT AUTO_INCREMENT PRIMARY KEY,
                                    `user_id` INT NOT NULL,
                                    `gender_id` INT NOT NULL,
                                    FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                    FOREIGN KEY (`gender_id`) REFERENCES `genders`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
CREATE TABLE `tokens` (
                          `id` INT AUTO_INCREMENT PRIMARY KEY,
                          `token` VARCHAR(255) NOT NULL,
                          `token_type` VARCHAR(50) NOT NULL,
                          `revoked` TINYINT(1) NOT NULL,
                          `expired` TINYINT(1) NOT NULL,
                          `user_id` INT NOT NULL,
                          FOREIGN KEY (`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                          KEY `Key` (`token`, `token_type`, `revoked`, `expired`)
);
CREATE TABLE `friend_requests` (
                                   `to_id` INT NOT NULL,
                                   `from_id` INT NOT NULL,
                                   `accepted` TINYINT(1),
                                   `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                   `message` NVARCHAR(255),
                                   PRIMARY KEY (`to_id`, `from_id`),
                                   FOREIGN KEY (`to_id`) REFERENCES `users`(`id`),
                                   FOREIGN KEY (`from_id`) REFERENCES `users`(`id`)
);

CREATE TABLE `friends` (
                           `user_id` INT NOT NULL,
                           `friend_id` INT NOT NULL,
                           `friend_since` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           `favorite` TINYINT(1) NOT NULL DEFAULT 0 CHECK (favorite IN (0,1)), -- yêu thích or quan trọng
                           `status` NVARCHAR(50), -- "bình thường", "tạm thời xa cách", "..."
                           PRIMARY KEY (`user_id`, `friend_id`),
                           FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
                           FOREIGN KEY (`friend_id`) REFERENCES `users`(`id`)
);

CREATE TABLE IF NOT EXISTS questions (
                                         question_id INT AUTO_INCREMENT PRIMARY KEY,
                                         question_text TEXT NOT NULL,
                                         question_type VARCHAR(255) -- Ví dụ: 'kiến tạo', 'trắc nghiệm'
);

-- Bảng cho các đáp án
CREATE TABLE IF NOT EXISTS answers (
                                       answer_id INT AUTO_INCREMENT PRIMARY KEY,
                                       question_id INT,
                                       answer_text TEXT NOT NULL,
                                       mbti_dimension VARCHAR(4), -- Ví dụ: 'E', 'I', 'N', 'S', 'T', 'F', 'J', 'P'
                                       score INT,
                                       FOREIGN KEY (question_id) REFERENCES questions(question_id)
);

CREATE TABLE IF NOT EXISTS mbti_results (
                                            mbti_type VARCHAR(4) PRIMARY KEY,
                                            description TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS user_results (
                                            result_id INT AUTO_INCREMENT PRIMARY KEY,
                                            user_id INT,
                                            mbti_type VARCHAR(4),
                                            test_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                            FOREIGN KEY (user_id) REFERENCES users(id),
                                            FOREIGN KEY (mbti_type) REFERENCES mbti_results(mbti_type)
);

-- Bảng cho các gói premium
CREATE TABLE `premium_packages` (
                                    `package_id` INT AUTO_INCREMENT PRIMARY KEY,
                                    `package_name` NVARCHAR(100) NOT NULL,
                                    `description` TEXT,
                                    `price` DECIMAL(10,2) NOT NULL,
                                    `duration` INT NOT NULL, -- Thời gian hiệu lực của gói, tính theo ngày
                                    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Bảng lịch sử mua hàng của người dùng
CREATE TABLE `user_purchases` (
                                  `purchase_id` INT AUTO_INCREMENT PRIMARY KEY,
                                  `user_id` INT NOT NULL,
                                  `package_id` INT NOT NULL,
                                  `purchase_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                  `expire_date` DATETIME NOT NULL, -- Ngày hết hạn của gói premium
                                  FOREIGN KEY (`user_id`) REFERENCES `users`(`id`),
                                  FOREIGN KEY (`package_id`) REFERENCES `premium_packages`(`package_id`)
);

-- Bảng cho các giao dịch thanh toán (nếu cần)
CREATE TABLE `transactions` (
                                `transaction_id` INT AUTO_INCREMENT PRIMARY KEY,
                                `purchase_id` INT NOT NULL,
                                `transaction_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                `amount` DECIMAL(10,2) NOT NULL,
                                `status` NVARCHAR(50), -- Ví dụ: 'Completed', 'Pending', 'Failed'
                                FOREIGN KEY (`purchase_id`) REFERENCES `user_purchases`(`purchase_id`)
);

-- Một bảng phụ cho các benefits của gói premium, nếu mỗi gói có nhiều benefits khác nhau
CREATE TABLE `package_benefits` (
                                    `benefit_id` INT AUTO_INCREMENT PRIMARY KEY,
                                    `package_id` INT NOT NULL,
                                    `benefit_description` TEXT NOT NULL,
                                    FOREIGN KEY (`package_id`) REFERENCES `premium_packages`(`package_id`)
);

-- Chèn dữ liệu vào bảng `genders`
INSERT INTO genders (name) VALUES ('Male'), ('Female'), ('Non-binary');

-- Chèn dữ liệu vào bảng `roles`
INSERT INTO roles (name) VALUES ('User'), ('Premium');

-- Chèn dữ liệu vào bảng `relationship_type`
INSERT INTO relationship_type (name) VALUES ('Friendship'), ('Romantic'), ('Professional');

-- Chèn dữ liệu vào bảng `profiles`
INSERT INTO profiles (avatar, intro, hobbies, work_at) VALUES
                                                           ('avatar1.jpg', 'Just a nerd who loves coding and cats.', 'Coding, cats', 'Tech Company'),
                                                           ('avatar2.jpg', 'Outdoorsy type, love hiking and nature.', 'Hiking, nature', 'Outdoor Equipment Co');

INSERT INTO users (first_name, last_name, nickname, password, gender_id, email, phone, address, date_of_birth, role_id, profile_id) VALUES
                                                                                                                                        ('Jane', 'Doe', 'jdoe', 'password123', 2, 'jane.doe@example.com', '1234567890', '123 Main St, Anytown, USA', '1990-01-01', 1, 1),
                                                                                                                                        ('John', 'Smith', 'jsmith', 'password456', 1, 'john.smith@example.com', '0987654321', '456 Main St, Anytown, USA', '1988-12-31', 1, 2);

-- Chèn dữ liệu vào bảng `feedbacks`
INSERT INTO feedbacks (description, rate) VALUES
                                              ('Great experience', 5),
                                              ('Could be better', 3),
                                              ('Not satisfied', 1);

-- Chèn dữ liệu vào bảng `social_accounts`
-- Lưu ý: Thay thế `user_id` bằng ID thực tế từ bảng `users`
INSERT INTO social_accounts (provider, provider_id, email, name, user_id) VALUES
                                                                              ('Facebook', 'fb12345', 'jane.doe@example.com', 'Jane Doe', 1),
                                                                              ('Google', 'google67890', 'john.smith@example.com', 'John Smith', 2);

INSERT INTO conversations (participant_1, participant_2, status, feedback_id) VALUES
                                                                                  (1, 2, 'Active', 1),
                                                                                  (2, 1, 'Closed', 2);

INSERT INTO interested_in_relation (user_id, relationship_type_id) VALUES
                                                                       (1, 1),
                                                                       (2, 2);

INSERT INTO messages (from_user_id, to_user_id, message_text, send_datetime) VALUES
                                                                                 (1, 2, 'Hi, how are you?', NOW()),
                                                                                 (2, 1, 'I am fine, thank you!', NOW());


INSERT INTO notifications (sender_id, recipient_id, message) VALUES
                                                                 (1, 2, 'You have a new message'),
                                                                 (2, 1, 'Your profile has been viewed');


-- Chèn dữ liệu vào bảng `premium_packages`
INSERT INTO premium_packages (package_name, description, price, duration) VALUES
                                                                              ('Basic Package', 'The basic premium package offering minimal extra features.', 9.99, 30),
                                                                              ('Standard Package', 'The standard premium package with more features.', 19.99, 30),
                                                                              ('Premium Package', 'The most advanced package with all features.', 29.99, 30);

INSERT INTO user_purchases (user_id, package_id, purchase_date, expire_date) VALUES
                                                                                 (1, 1, NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY)),
                                                                                 (1, 2, DATE_ADD(NOW(), INTERVAL 31 DAY), DATE_ADD(NOW(), INTERVAL 61 DAY));

-- Chèn dữ liệu vào bảng `transactions`
-- Lưu ý: Thay đổi purchase_id tùy thuộc vào ID thực tế của bảng user_purchases
INSERT INTO transactions (purchase_id, transaction_date, amount, status) VALUES
                                                                             (1, NOW(), 9.99, 'Completed'),
                                                                             (2, DATE_ADD(NOW(), INTERVAL 31 DAY), 19.99, 'Completed');


-- Chèn dữ liệu vào bảng `package_benefits`
-- Lưu ý: Thay đổi package_id tùy thuộc vào ID thực tế của bảng premium_packages
INSERT INTO package_benefits (package_id, benefit_description) VALUES
                                                                   (1, 'Access to basic premium features.'),
                                                                   (2, 'Access to standard premium features including all basic package benefits.'),
                                                                   (3, 'Access to all premium features available.');


select * from users


