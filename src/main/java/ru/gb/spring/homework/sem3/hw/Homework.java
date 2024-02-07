package ru.gb.spring.homework.sem3.hw;

import jakarta.transaction.Transactional;

@Transactional
public class Homework {

  /**
   * Задание для 3 семинара
   * 0. Переварить все, что было изучено.
   * 1. Доделать сервис управления книгами:
   * 1.1 Реализовать контроллер по управлению книгами с ручками:
   * GET /book/{id} - получить описание книги,
   * DELETE /book/{id} - удалить книгу,
   * POST /book - создать книгу
   * 1.2 Реализовать контроллер по управлению читателями (аналогично контроллеру с книгами из 1.1)
   * 1.3 В контроллере IssueController добавить ресурс
   * GET /issue/{id} - получить описание факта выдачи
   *
   * 2.1 В сервис IssueService добавить проверку, что у пользователя на руках нет книг. Если есть - не выдавать книгу (статус ответа - 409 Conflict)
   * 2.2 В сервис читателя добавить ручку GET /reader/{id}/issue - вернуть список всех выдачей для данного читателя
   *
   * 3.1* В Issue поле timestamp разбить на 2: issued_at, returned_at - дата выдачи и дата возврата
   * 3.2* К ресурс POST /issue добавить запрос PUT /issue/{issueId}, который закрывает факт выдачи. (т.е. проставляет returned_at в Issue).
   * Замечание: возвращенные книги НЕ нужно учитывать при 2.1
   * 3.3** Пункт 2.1 расширить параметром, сколько книг может быть на руках у пользователя.
   * Должно задаваться в конфигурации (параметр application.issue.max-allowed-books). Если параметр не задан - то использовать значение 1.
   */

    /**
     * Задание для 4 семинара
     * 1. В предыдущий проект (где была библиотека с книгами, выдачами и читателями) добавить следующие ресурсы,
     * которые возвращают готовые HTML-страницы (т.е. это просто Controller'ы).
     * 1.1 /ui/books - на странице должен отобразиться список всех доступных книг в системе
     * 1.2 /ui/reader - аналогично 1.1
     * 1.3 /ui/issues - на странице отображается таблица, в которой есть столбцы
     * (книга, читатель, когда взял, когда вернул (если не вернул - пустая ячейка)).
     * 1.4* /ui/reader/{id} - страница, где написано имя читателя с идентификатором id и перечислены книги,
     * которые на руках у этого читателя.
     *
     * Чтобы шаблонизатор thymeleaf заработал, то нужно добавить зависимость в pom.xml:
     *        <dependency>
     *            <groupId>org.springframework.boot</groupId>
     *            <artifactId>spring-boot-starter-thymeleaf</artifactId>
     *        </dependency>
     */

    /**
     * Задание для 5 семинара
     * 1. Подключить базу данных к проекту "библиотека", который разрабатывали на прошлых уроках.
     * 1.1 Подключить spring-boot-starter-data-jpa (и необходимый драйвер) и указать параметры соединения в application.yml
     * 1.2 Для книги, читателя и факта выдачи описать JPA-сущности
     * 1.3 Заменить самописные репозитории на JPA-репозитории
     *
     * Замечание: базу данных можно использовать любую (h2, mysql, postgres).
     */

    /**
     * Задание для 6 семинара
     * 1. Подключить OpenAPI 3 и swagger к проекту с библиоткой
     * 2. Описать все контроллеры, эндпоинты и возвращаемые тела с помощью аннотаций OpenAPI 3
     * 3. В качестве результата, необходимо прислать скриншот(ы) страницы swagger (с ручками)
     *
     * Доп. задание (сдавать не нужно):
     * придумать какие-то доменные сервисы (по типу библиотеки и заметок) и попытаться спроектировать его API.
     */

    /**
     * Задание для 7 семинара
     * 1. Для ресурсов, возвращающих HTML-страницы, реализовать авторизацию через login-форму.
     * Остальные /api ресурсы, возвращающие JSON, закрывать не нужно!
     * 2.1* Реализовать таблицы User(id, name, password) и Role(id, name), связанные многие ко многим
     * 2.2* Реализовать UserDetailsService, который предоставляет данные из БД (таблицы User и Role)
     * 3.3* Ресурсы выдачей (issue) доступны обладателям роли admin
     * 3.4* Ресурсы читателей (reader) доступны всем обладателям роли reader
     * 3.5* Ресурсы книг (books) доступны всем авторизованным пользователям
     */

    /**
     * Задание для 8 семинара
     * 1. Создать аннотацию замера времени исполнения метода (Timer). Она может ставиться над методами или классами.
     * Аннотация работает так: после исполнения метода (метода класса) с такой аннотацией, необходимо в лог записать следующее:
     * className - methodName #(количество секунд выполнения)
     *
     * 2.* Создать аннотацию RecoverException, которую можно ставить только над методами.
     * <code>
     *   @interface RecoverException {
     *     Class<? extends RuntimeException>[] noRecoverFor() default {};
     *   }
     * </code>
     * У аннотации должен быть параметр noRecoverFor, в котором можно перечислить другие классы исключений.
     * Аннотация работает так: если во время исполнения метода был экспешн, то не прокидывать его выше
     * и возвращать из метода значение по умолчанию (null, 0, false, ...).
     * При этом, если тип исключения входит в список перечисленных в noRecoverFor исключений,
     * то исключение НЕ прерывается и прокидывается выше.
     * 3.*** Параметр noRecoverFor должен учитывать иерархию исключений.
     *
     * Сдавать ссылкой на файл-аспект в проекте на гитхабе.
     */
}
