# 🔐 BCrypt dans HUNVRE — ce qu'on a intégré

## D'où vient le JAR ?

Téléchargé sur **mvnrepository.com** — c'est le dépôt de référence pour les libs Java.
- Groupe : `org.mindrot`
- Artifact : `jbcrypt`
- Version : `0.4`
- Lien : https://mvnrepository.com/artifact/org.mindrot/jbcrypt/0.4

Sur la page, section **FILES** → clic sur **jar (17 KB)** → c'est tout.

---

## C'est quoi BCrypt et pourquoi on s'en sert ?

BCrypt c'est une lib de hachage de mots de passe. Le principe : au lieu de stocker le mot de passe en clair en BDD (baaaad idea 🙈), on stocke un **hash** — une chaîne de 60 caractères générée à partir du mot de passe.

Ce hash est **unidirectionnel** : impossible de retrouver le mot de passe original à partir du hash. Concrètement, quand un utilisateur se connecte, BCrypt récupère le sel intégré dans son hash stocké en BDD, rehashe le mot de passe qu'il vient de saisir avec ce même sel, et compare les deux résultats. Si c'est identique → accès autorisé. Le mot de passe en clair ne sort jamais de la BDD parce qu'il n'y est tout simplement pas.

Bonus important : BCrypt intègre automatiquement un **sel** (salt) aléatoire à chaque hash. Résultat : deux utilisateurs avec le même mot de passe auront des hashs complètement différents en BDD. Ça empêche les attaques par tables précalculées.

---

## Les méthodes utiles

```java
// Génère un sel aléatoire (indispensable pour hashpw)
BCrypt.gensalt()

// Prend le mdp en clair + le sel, retourne le hash (60 chars)
BCrypt.hashpw(mdp, BCrypt.gensalt())

// Extrait le sel du hash BDD, rehashe le mdp saisi, compare → retourne true ou false
BCrypt.checkpw(mdpSaisi, hashBDD)
```

- `hashpw` → utilisé à **l'inscription** dans `ControleurCreationCompte`
- `checkpw` → utilisé à **la connexion** dans `ControleurConnexion` (à venir)

---

## Le cost factor — c'est quoi les "tours" ?

`gensalt()` accepte un paramètre optionnel : le **cost factor**.

```java
BCrypt.gensalt()     // cost = 10 par défaut (on n'a pas mis de paramètre)
BCrypt.gensalt(12)   // cost = 12, calcul plus long
```

"Tourner" ça veut dire que l'algorithme répète son calcul **2^cost fois**. Avec le défaut à 10, c'est 2^10 = **1024 répétitions**. Avec 12, c'est 2^12 = 4096 répétitions.

C'est **voulu** : c'est le calcul lui-même qui est rendu lent, pas l'appli. Si un attaquant vole notre BDD et veut tester des millions de mots de passe en force brute, chaque tentative lui coûte du temps machine. Avec cost=10 (~100ms par hash), tester 1 million de mots de passe = ~28 heures.

Dans HUNVRE on a utilisé `BCrypt.gensalt()` sans paramètre — ce qui est strictement équivalent à `BCrypt.gensalt(10)` puisque 10 est la valeur par défaut. On est donc déjà à 10 tours sans le savoir. 😄

---

## Anatomie du hash : `$2a$10$se.pZkGmEdbd39aOp6TDeOaXsySj0QFSoeZ/HG9EH6n...`

Un hash BCrypt c'est pas du charabia aléatoire — c'est une chaîne de **60 caractères** structurée en 4 segments collés ensemble :

```
$2a$   10$   se.pZkGmEdbd39aOp6TDe   OaXsySj0QFSoeZ/HG9EH6n...
 [1]   [2]          [3]                          [4]
```

- **[1] `$2a$`** — 4 caractères — version de l'algorithme BCrypt
- **[2] `10$`** — 3 caractères — cost factor (le nombre de tours)
- **[3] 22 caractères** — le sel généré aléatoirement à chaque inscription
- **[4] 31 caractères** — le hash du mot de passe combiné avec le sel

Tout est dans une seule chaîne — c'est pour ça que `checkpw` n'a pas besoin qu'on lui passe le sel séparément : il l'extrait du segment [3] tout seul.

---

## Les erreurs que j'ai eues 😅

### 1. `java.lang.ClassNotFoundException: org.mindrot.jbcrypt.BCrypt`

Cette erreur est apparue dans mon navigateur sous forme de page HTTP 500 au moment de soumettre le formulaire de création de compte. Tomcat ne trouvait pas le JAR à l'exécution alors qu'il était bien dans le Build Path Eclipse.

**La différence entre Build Path et dossier de déploiement :**

Le **projet source** c'est ce qu'Eclipse voit et compile — notre dossier `Project-HUNVRE-web` dans `eclipse-workspace`. Quand on ajoute un JAR au Build Path, Eclipse sait où le trouver pour compiler le code.

Mais Tomcat ne travaille pas directement sur le projet source. Il travaille sur une **copie déployée** qu'il maintient dans un dossier caché, géré par Eclipse dans les coulisses et invisible depuis le projet :

`C:\Users\Greta\eclipse-workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\`

C'est là que vit la vraie appli qui tourne. Et le Clean+Restart d'Eclipse n'a pas copié le JAR dans ce dossier automatiquement sur ma machine.

**La solution :** glisser-déposer `jbcrypt-0.4.jar` directement dans le dossier `WEB-INF/lib` depuis l'explorateur de fichiers vers Eclipse. Eclipse crée les liens sous-jacents automatiquement et refreshe — c'est la bonne façon de faire.

⚠️ À retenir : quand on ajoute un JAR au projet, il faut le mettre dans le **Build Path** (`.classpath`) ET le glisser-déposer dans `WEB-INF/lib` depuis l'explorateur vers Eclipse.

### 2. `com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data too long for column 'mdp' at row 1`

Cette erreur Java est apparue dans ma console Eclipse au moment où Tomcat exécutait l'INSERT. BCrypt avait bien fonctionné — le hash était généré — mais au moment d'écrire en BDD, MySQL a refusé : la colonne `mdp` était en `VARCHAR(30)` et un hash BCrypt fait toujours **60 caractères**. Ça rentrait pas. 😬

---

## La modif BDD — pourquoi 60 chars ?

Un hash BCrypt fait **toujours exactement 60 caractères**, sans exception. C'est la taille fixe des 4 segments : 4 + 3 + 22 + 31 = 60 caractères, tout concatené.

Si la colonne `mdp` est plus petite, MySQL **refuse l'insertion** avec une erreur `Data too long` → personne ne peut créer de compte. 🔥

La commande à exécuter sur **chaque BDD locale** de l'équipe via phpMyAdmin → onglet SQL :

```sql
ALTER TABLE utilisateur MODIFY mdp VARCHAR(60);
```

> 💡 Pro tip : on peut mettre `VARCHAR(72)` pour avoir de la marge — BCrypt n'utilise de toute façon que les 72 premiers caractères d'un mot de passe.

---

## ⚠️ Point important pour l'équipe

Vos comptes existants en clair en BDD **ne fonctionneront plus** une fois `ControleurConnexion` migré à BCrypt. Il faudra les recréer via le formulaire de création de compte.
