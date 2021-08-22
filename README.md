# Recipes
 Spring boot + data jpa + security
 
 Это проект, который я дорабатываю для отработки тех или иных навыков работы со спрингом.
 В будущем это должен быть сайт с рецептами, который позволяет составить список покупок и отправить тебе его в ТГ.
 
 Что есть сейчас?
 - регистрация нового пользователя
 - добавление рецептов авторизованными пользователями
 - просмотр списка своих рецептов, а так же их изменение/удаление
 - поиск среди всех рецептов по категории, имени или id

Что в планах добавить?
- список покупок
- тестирование с jUnit
- какой-то фронтенд
- написать и прикрутить бота, который скидывает список покупок в тг

Как это работает сейчас?
Почти все функции доступны только зарегистрированным пользователям, поэтому сначала нужно сделать регистрацию.
Для регистрации необходимом сделать POST /api/register с JSON-ом следующего содержания:

{
   "userName": "Cook_Programmer",
   "password": "RecipeInBinary"
}
Поле имени пользователя не должно быть пустым, пароль должен содержатт минимум 8 символов.

 
