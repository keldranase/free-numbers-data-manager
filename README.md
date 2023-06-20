
### About
- Spring boot app, дающий эндпоинт, вызов которого собирает данные из API onlinesim, возвращает его в формате .json и в зависимости от переданных параметров сохраняет информацию в базе данных


### How to Run?
- Готовая run configuration находится здесь: [run-configuration](https://github.com/keldranase/free-numbers-data-manager/tree/master/.run-configuration)
- В vm options нужно указать корневую папку для логов, e.g. -Dfree.sim.data.manager.logs.folder=/Users/klepovaleks/log/onlinesim.data.manager
- curl чтобы получить список номеров по всем странам и сохранить их в бд
```
curl --location --request POST 'localhost:8080/api/v1/freeNumbers/byCountry?doUpdateDb=true'
```

### Что я хотел бы сделать при наличии большего времени
1. Добавить функциональные тесты. Тесты это супер важная вещь, сокращающая время на разработку, уменьшающая нагрузку на ручное тестирование, и снижающая количество багов в проде
2. Тесты настолько важны, что вот второй пункт про важность тестов.
3. Перевести конфигурацию с spring-logback на log4j, тк последний представляет больше возожностей и более гибок
4. Дать более осмысленные имена классам/переменным/методам (например countryCode -> countryId, тк под countryCode часто понимают код перед номером (вроде +7))
5. Организовать проперти и конфиги - сейчас все свалено в resources, нет разделения на папки, нет рзаделения на тестовые/продовые проперти
6. Организовать проперти внутри конфигов. Например сейчас коннект к дб сконфигурирован через дифолтный datasource, что не есть хорошо, если мы захотим добавть другую дб в проект
7. Вместо удаления номеров, иметь поле isDeleted и проставлять его в true
8. Гипотетически, при аналогичном но высокозагруженном проекте, хорошо было бы добавить что-нибудь вроде Apache Kafka, так, чтобы вместо вызова методов логика работала на примере pub-sub. Полученные от клиента номера помещались бы в очередь, откуда забирались бы заполнителем postgresql, выполняющим роль OLTP, заполнителем какой-нибудь OLAP базы, вроде ClickHouse, если нам вдруг нужна аналитика, логгером и.т.д.


### Первоначальный фидбек на задание: с чем и почему я не согласен

Первая линия проверки дала фидбек на переделывание, но в процессе разговора с HR'ом договорились отправить его тебе, хотя бы потому, что такой формат решения мне кажется более интересным

Признаю мое участие в недопонимании - видя "тестовое задание" я интерпретировал его свободно, как будто нам надо создать проект, который теоретически можно было бы встретить в дикой природе, не только на масштабе запроса и обработки пары дюжин номеров, а с замахом на что-то интересное. Изначально хотел поставить проект на quartz, переодически запускающий обработку, в процессе которой через Kaffk'у бы результаты отправлялись в другие компоненты сохранения в базы, уведомлений, чтобы лишний раз поиграться/попрактиковаться с этими технологиями

__Пункты первого уровня - фидбек, второго - моя аргументация.__
1. использовать проект без спринга.
    - Как показано дальше, формулировака склоняет в сторону web-app
    - При условии, что задание понимается как "создать web-app", spring кажется самым "взрослым", "богатым на фичи", "расширяемым" и "удобным в работе с" решением.
    - В ТЗ сказано "минимальное количество зависимостей", но это не подразумевает под собой запрет спринга
2. я не должен что то дергать чтобы получить данные, я должен лишь запустить метод - это может быть простомейн класс
    - Джава проект, который работает как питон скрипт: "запустил - получил результат" - неочевидный формат решения подобной задачи, особенно когда дальнейшие пункты намекают на БД
    - "ступенчатая обработка ошибок" тоже намекает на проект, где она действительно стоит того, то есть "web-app", а не "скрипт"
3. взаимодействие с бд не требуется в рамках задания, просьба сделать именно то что написано по тз
    - В ТЗ сказано "Создать удобную структуру для хранения и быстрой работы с собранными данными"
    - Когда говорится о хранении, а особенно для _быстрой_ работы, самым логичным ответом выглядит БД, тк они _буквально созданы для этих задач_. Еще можно подумать о бинарном формате, но это явно не сюда
4. оставить концепцию с мапой, но попробовать доработать ее детально чтобы работа с данными была производительной.
    - Принял, но "производительно" слишком зависит от конкретного кейса. Я предположил необходимость вывода списка по странам - сделал под него. Вероятно не все поля сущности "номер" нужны потребителям - но об этом не могу знать. Ключами для стран сделал "id стран", но в зависимости от построения системы имена стран так же могут использоваться. 
5. проверить типы данных в своих моделях dto и view
    - Принял, поправил где увидел, добавил комменты, где думал могут быть вопросы






