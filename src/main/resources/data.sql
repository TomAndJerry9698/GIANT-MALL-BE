-- user 테이블 샘플 데이터 (실서비스에서는 암호화 필요)
INSERT INTO `user` (`email`, `password`, `name`, `phone`, `role`, `created_at`, `updated_at`)
VALUES
('buyer@naver.com',  'buyer123!', '송어진',  '01012345601', 'BUYER',  NOW(), NULL),
('seller@naver.com', 'seller123!', '오승택', '01012345602', 'SELLER', NOW(), NULL),
('admin@naver.com',  'admin123!',  '김관리',  '01012345603', 'ADMIN',  NOW(), NULL);

-- product 테이블 샘플 데이터
INSERT INTO `product` (`name`, `description`, `price`, `category_id`, `seller_id`, `created_at`, `updated_at`)
VALUES
('유기농 사과',        '신선한 국내산 유기농 사과입니다.',                    12000.00, NULL, 2,  NOW(), NULL),
('블루투스 스피커',    '고출력 무선 블루투스 스피커.',                        55000.00, NULL, 2,  NOW(), NULL),
('초코쿠키 10개입',    '수제 초콜릿 쿠키 세트.',                              8000.00,  NULL, 5,  NOW(), NULL),
('노트북 거치대',      '알루미늄 재질 각도조절 노트북 거치대.',                25000.00, NULL, 8,  NOW(), NULL),
('여름 반팔티',        '면 100% 시원한 남여공용 반팔티셔츠.',                  15000.00, NULL, 2,  NOW(), NULL),
('텀블러 500ml',       '친환경 스테인리스 텀블러, 500ml 용량.',                18000.00, NULL, 8,  NOW(), NULL),
('유선 마우스',        '내구성 좋은 USB 유선 마우스.',                        7000.00,  NULL, 5,  NOW(), NULL),
('안마기',             '어깨, 허리 모두 사용 가능한 무선 안마기.',             65000.00, NULL, 2,  NOW(), NULL),
('수분크림',           '민감성 피부용 저자극 수분크림.',                      28000.00, NULL, 8,  NOW(), NULL),
('프리미엄 원두커피',   '산지직송 프리미엄 아라비카 원두커피 1kg.',            39000.00, NULL, 5,  NOW(), NULL);
