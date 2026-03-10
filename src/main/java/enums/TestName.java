package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TestName {

    INSERT_USER_TEST_POS("Добавление новых пользователей в таблицу users"),
    INSERT_ROLE_TEST_POS("Добавление новых ролей в таблицу roles"),
    SYNC_USERS_ROLES_TEST("Синхронизация пользователей с их ролями в таблице users_roles"),
    UPDATE_USER_ROLES_TEST("Обновление ролей пользователя в таблице users_roles"),
    INSERT_USER_TEST_NEG("Добавление нового пользователя в таблицу users" +
                                  "(нег - нарушение уникальности ПК)"),
    INSERT_ROLE_TEST_NEG("Добавление новой роли в таблицу roles(нег - нарушение уникальности ПК)"),
    INSERT_USER_TEST_NULL_NEG("Добавление нового пользователя в таблицу users" +
                                       "(нег - нарушение требования к полю NotNull)"),
    UPDATE_USER_TEST_POS("Обновление существующего пользователя в таблице users"),
    UPDATE_USER_TEST_NEG("Обновление существующего пользователя в таблице users(нег)"),
    UPDATE_ROLE_TEST_POS("Обновление существующей роли в таблице roles"),
    UPDATE_ROLE_TEST_NEG("Обновление существующей роли в таблице roles(нег)"),
    DELETE_USER_TEST("Удаление существующего пользователя из таблицы users"),
    DELETE_ROLE_TEST("Удаление существующей роли из таблицы roles");

    private final String operation;

    public String camel() {
        String name = this.name();
        String[] words = name.split("_");
        StringBuilder camelCase = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            if (i == 0) {
                camelCase.append(word);
            } else {
                camelCase
                        .append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1));
            }
        }
        return camelCase.toString();
    }
}