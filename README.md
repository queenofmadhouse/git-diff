# Git Diff

#### Java command-line tool designed to identify files that have been modified in both a remote GitHub branch and a local Git branch. Created by Eva Kalachik (Evgenia Chendeva) as a test task

### Prerequisites

1. JDK 21 or higher
2. Git
3. GitHub Personal Access Token [LINK](https://github.com/settings/tokens) with access to commits statuses

### Installation
#### Clone the Repository:
~~~bash
git clone git@github.com:queenofmadhouse/git-diff.git
~~~

#### Navigate to the Project Directory:
~~~bash
cd git-diff
~~~

#### Build the Project
~~~bash
mvn clean package
~~~

### Usage

~~~bash
java -jar target/gitdiff-1.0.jar <owner> <repo> <accessToken> <localRepoPath> <remoteBranch> <localBranch>
~~~

#### Parameters:

* owner: The GitHub username or organization name that owns the repository.
* repo: The name of the repository.
* accessToken: Your GitHub personal access token for API authentication.
* localRepoPath: The file path to your local Git repository.
* remoteBranch: The name of the remote branch to compare.
* localBranch: The name of the local branch to compare.

#### Example:
~~~
java -jar gitdiff-1.0.jar queenofmadhouse HelloWorld ghp_XXX /path/to/local/repo main feature-branch
~~~

### License

This project is licensed under the MIT License. See the [LICENSE](https://github.com/queenofmadhouse/git-diff/blob/main/LICENSE) file for details.