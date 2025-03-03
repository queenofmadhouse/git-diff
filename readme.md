# Git Diff

#### Java command-line tool designed to identify files that have been modified in both a remote GitHub branch and a local Git branch. Created by Eva Kalachik (Evgenia Chendeva) as a test task

### Prerequisites

1. JDK 21 or higher
2. Git
3. GitHub Personal Access Token

### Installation
#### Clone the Repository:
~~~bash
git@github.com:queenofmadhouse/git-diff.git
~~~

#### Navigate to the Project Directory:
~~~bash
cd gitdiffcli
~~~

#### Build the Project
~~~bash
mvn clean package
~~~

### Usage

~~~bash
java -jar target/gitdiffcli.jar <owner> <repo> <accessToken> <localRepoPath> <branchA> <branchB>
~~~

#### Parameters:

* <owner>: The GitHub username or organization name that owns the repository.
* <repo>: The name of the repository.
* <accessToken>: Your GitHub personal access token for API authentication.
* <localRepoPath>: The file path to your local Git repository.
* <branchA>: The name of the remote branch to compare.
* <branchB>: The name of the local branch to compare.

License

This project is licensed under the MIT License. See the [LICENSE]() file for details.