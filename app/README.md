
L'utilisation de Room avec Jetpack Compose est simple. Tout d'abord, créez une classe qui étend RoomDatabase et annotez-la avec @Database. Cela spécifiera le nom de la base de données, la version et les entités qui font partie de la base de données. Ensuite, créez une interface DAO (Data Access Object) qui contient les méthodes permettant d'interagir avec la base de données. Enfin, créez une classe qui étend Room et implémente l'interface DAO. Cette classe contiendra les requêtes SQL réelles pour la base de données.

La liste des entités fournies à l'annotation @Database définit les tables SQLite dans la base de données et sera traitée ultérieurement. Les développeurs doivent créer une classe Entity pour chaque table de la base de données. Cette classe définit la structure des données qui seront stockées dans la base de données. Dans ce cas, nous allons créer une entité User.

@Database(
version = 2,
entities = [User::class],
autoMigrations = [
AutoMigration (from = 1, to = 2)
]
)

Ex(pour passer de la version 2 à 3):

@Database(
version = 3,
entities = [User::class],
autoMigrations = [
AutoMigration (from = 1, to = 2),
AutoMigration (from = 2, to = 3)
]
)

La valeur tableName est facultative et peut être utilisée pour donner à la table un nom différent de celui de l'entité.

Une clé primaire estrequise pour toutes les entités. Il s'agit d'une valeur unique qui identifie chaque entité. Si elle est définie sur un type entier, l'activation de la génération automatique (comme dans l'exemple ci-dessus) permettra à SQLite d'incrémenter automatiquement la valeur pour le développeur lorsqu'une nouvelle entité est insérée.

Enfin, les développeurs devront créer un objet d'accès aux données, ou DAO. Il s'agit de la classe qui fournira les méthodes d'accès et de modification des données dans la base de données.