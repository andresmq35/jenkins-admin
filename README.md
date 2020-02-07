## Tutorial Links
- http://tdongsi.github.io/blog/2018/02/09/intellij-setup-for-jenkins-shared-library-development/
- https://github.com/tdongsi/jenkins-steps-override

## Build
- ```./gradlew build```

## Gradle Jenkins

- Allows maintaining jenkins configs in source control
- Run jenkins jobs via gradle

#### Example
```
   apply plugin: 'com.terrafolio.jenkins'
   	
   jenkins {
   	servers {
   		testing {
   			url 'http://jenkins.somewhere.com:8080'
   			secure true         // optional
   			username "testuser" // optional
   			password "testpass" // optional
   		}
   	}
   	        
   	defaultServer servers.testing // optional
   	jobs {
   		test {
   			server servers.testing
   			definition {
   				name "Build ${project.name}" //optional
   				xml file('config.xml')
   			}
   		}
   	}
   }
```
