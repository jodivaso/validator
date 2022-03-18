# Validator

This is a tool to help students with the projects for the course ***Computer Systems*** (which belongs to the computer science degree 
and the degree in mathematics) of University of La Rioja.

The tool provides feedback on some of the imposed requirements of the projects. The tool is open-source and can be adapted by other teachers in other contexts.


https://user-images.githubusercontent.com/50442034/158994595-d2a3c020-848d-4102-ab5c-4c8f7a2be470.mp4


## Requirements checked by the tool

- Each HTML file must have a well-formed markup, i.e., they must pass the validator 
by the World Wide Web Consortium (W3C) for HTML version 5.
-  Each CSS must be validated by the W3C CSS Validation Service for CSS version 3.
- Contents and styling must be completely distinguished, i.e., the HTML
files must be focused on the contents and their structure and they must not include any
attribute about the aspect or the style of the website.
- There must be at least eight different HTML files. 
- There must be at least two different CSS files.
- All the HTML files must be linked to a CSS file and, at least, two of them
must be linked to at least two different CSS files.
- All the HTML pages must include their corresponding HTML charset attribute.
- The websites must be uploaded to the server for software engineering
and mathematics students, which is 
provided by the university.
- The entire website must work properly if it is migrated to another server. To this end, the local resources (including images) must be linked using 
relative paths.
- All the HTML pages must include the HTML and CSS validation icons, which must be linked 
via a relative path and without using style attributes in the HMTL tags.


## Customize the requirements
The tool is customizable, in the sense that the requirements can be changed. If the ```configure.txt``` file is present, the tool will load that configuration. 
Otherwise, the default setup is loaded.

The  ```configure.txt``` permits customizing the requirements. Write -1 to disable a requirement, or write a concrete number to impose the corresponding restriction.

For example:

```
validateHTML=-1
validateCSS=1
numHTML=9
numCSS=2
HTMLwithoutCSS=0
HTMLwith2CSS=2
HTMLnoCharset=0
numAspect=-1
AbsoluteLinks=0
universityServer=-1
check_user_name=-1
```

This means that HTML files are not validated, CSS are validated, there must be at least 9 HTML files and 2 CSS files, all files must be linked to at least 1 CSS and 2 
must be linked to at least two CSS, all files must have a charset attribute, there are no local resources linked via absolute links, no search of style attributes in HTML tags, 
no check about  no check about uploading the project to the university server and the user name is not checked.

## Run the tool
Simply create a Java project in your favorite IDE. Unzip the file ```libraries.zip``` and add those libraries to the project. Run ```ValidatorStart.java```.
Some projects are included in the folder ```Project examples``` that can be used to test the tool. Tested with JDK 1.8.

