# Проект Kafka

👋 Привет! Меня зовут Анастасия.  
Это мой проект, где я тренируюсь с различными технологиями.
Изначально проект был разбит на ветки, а ветки представляли отдельный мини-проекты с конкретной функциональностью, но я, для удобства демонстрации, решила разбить его на отдельные проекты для каждой ветки.
пше 

## 🌟 О проекте
**Kafka** — проект, добавляющий возможность асинхронного взаимодействия через Kafka. Для экспериментов используются слушатели и продюсеры самого проекта.

### 🎯 Цели проекта
- Тренировка и изучение новых технологий.
- Создание готовой основы для других проектов.
- Упрощение разработки с помощью уже настроенных инструментов.

## 🛠️ Функциональность
Проект включает:
- **Kafka** — для асинхронного взаимодействия.
- **Multithreading** — для параллельной обработки топиков.

## 🔍 Что стоит посмотреть?

Этот проект на основе Rest и добавляет следующие классы:

- *docker-compose.local.yaml* - для развертывания Kafka в контейнере.
- *resources.application.yaml* - для настройки Kafka через Spring Boot.
- *config.AsyncConfig.java* - настройка пула потоков.
- *config.kafka.KafkaConsumerConfig.java* - настройка слушателей.
- *config.kafka.KafkaProducerConfig.java* - настройка продюсеров.
- *controller.KafkaController* - контроллер для экспериментов с Kafka.
- *service.kafka.KafkaCustomer* - сервис для слушателей Kafka.
- *service.kafka.KafkaProducer* - сервис для продюсеров Kafka.
- *Три объекта Kafka* 
  - *domain.kafka.ImageKafka* - маркерный интерфейс, указывает что объект можно передать Json-форматом.
  - *domain.kafka.NameKafka* - объект для передачи через Kafka.
  - *domain.kafka.RequisitesKafka* - объект для передачи через Kafka.

## ✨ Рекомендую посмотреть другие проекты:
1. [Rest - основа всего](https://github.com/AnastasiyaVetrova/Rest)
2. [SLF4J](https://github.com/AnastasiyaVetrova/SLF4J)
3. [ELK](https://github.com/AnastasiyaVetrova/ELK)
4. [Starter](https://github.com/AnastasiyaVetrova/Starter)
5. [Kubernetes](https://github.com/AnastasiyaVetrova/Kubernetes)
6. [Mongo(в работе)](https://github.com/AnastasiyaVetrova/Mongo)

## 🤝 Контакты
Если у вас есть вопросы или предложения, вы можете связаться со мной:
- **Email:** vetrova600@yandex.ru
- **Telegram:** @VetrovaAnastasy

Спасибо за внимание! 😊
