# Read Me First

# Getting Started

To check ssh permission:
> ssh -T git@github.com

To add ssh keys in windows:
> ssh-keyscan github.com >> %HOMEPATH%/.ssh/known_hosts

To add ssh keys in linux:
> mkdir $HOME/.ssh
> 
> ssh-keyscan github.com >> $HOME/.ssh/known_hosts
>
> echo ssh_key_contents >> $HOME/.ssh/id_ed25519
> 
> chmod 600 $HOME/.ssh/id_ed25519
> 
> git config --global user.email "you@example.com"
> 
> git config --global user.name "Your Name"


## Git Rest API

**Create branch**

> Make GET request to: 
> https://api.github.com/repos/pks9862728888/github-code-generator-file-storage/git/refs/heads
> 
> Then make POST request to:
> https://api.github.com/repos/pks9862728888/github-code-generator-file-storage/git/refs
> 
> `
{ 
"ref": "refs/heads/new-branch-name",
"sha": "76756f4ab468cc50abc6bb437969c772b7336a0c"
}
`

**Raise Pull request**

> Make POST request to:
> https://api.github.com/repos/pks9862728888/github-code-generator-file-storage/pulls
> 
> `
{
"title": "PR title",
"body": "PR Description",
"head": "new-branch-name",
"base": "master"
}
`

## Java Git operations

**To know current branch:**
> git rev-parse --abbrev-ref HEAD

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.1/maven-plugin/reference/html/#build-image)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#using.devtools)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#appendix.configuration-metadata.annotation-processor)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.7.1/reference/htmlsingle/#web)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

