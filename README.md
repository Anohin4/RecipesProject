# Recipes
 Spring boot + data jpa + security
 
 Это проект, который я дорабатываю для отработки тех или иных навыков работы со спрингом.
 В будущем это должен быть сайт с рецептами, который позволяет составить список покупок и отправить тебе его в ТГ.
 
 ### Что есть сейчас?
 - регистрация нового пользователя
 - добавление рецептов авторизованными пользователями
 - просмотр списка своих рецептов, а так же их изменение/удаление
 - поиск среди всех рецептов по категории, имени или id

### Что в планах добавить (план реализации совпадает с порядком пунктов)?
- тестирование с jUnit
- какой-то фронтенд
- реализовать список покупок
- написать и прикрутить бота, который скидывает список покупок в тг

____

Как это работает сейчас?

Почти все функции доступны только зарегистрированным пользователям, поэтому сначала нужно зарегистрироваться.


Если не залогиниться, то в ответ на большинство действий будет получена ошибка 403. Редирект на экран логина был отключен намеренно.

Метод аутентификации - basic auth.

Страница для логина /login, но если провять через postman, то просто внесите данные во вкладку Authorization.

Для регистрации необходимом сделать POST /api/register с JSON-ом следующего вида:
```
{
   "userName": "CookProgrammer",
   "password": "RecipeInBinary"
}
```
Поле имени пользователя не должно быть пустым, пароль должен содержатт минимум 8 символов.

 После этого вы можете использовать функции ниже:
 1. Добавление рецепта
```
    POST /api/recipe/new
```
   в теле запроса должен быть JSON следующего вида: 
 ```
 {
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "3fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
}
```

 2. Удаление рецета
 ```
    DELETE /api/recipe/{id}
 ```
 Вместо {id} указать id рецепта. Действие возможно только для зарегистрированных пользователей, остальные получат ошибку 403.
 
 3. Просмотр своих рецептов
 ```
    GET /api/recipe/myRecipe
 ```
 4. Поиск рецептов
 
     По ид
 ```
     GET /api/recipe/{id}
  ```
   Вместо {id} указать id рецепта. Действие возможно только для зарегистрированных пользователей, остальные получат ошибку 403.
  
   По категории или имени:
  
    GET api/recipe/search c параметром name или category.
     
   При поиске по имени, поиск будет производиться на наличие указанного слова в названии и результат будет отсортирован по дате внесения в программу.
   При поиске по категории будут выданы результаты указанной категории, сортировка по дате.
  
 5. Обновление и удаление рецептов
 ```
    DELETE /api/recipe/{id}
      
    PUT /api/recipe/{id}
 ```
   Вместо {id} указать id рецепта. При обновлении, в теле запроса должен быть обновленный рецепт по примеру выше.
   
