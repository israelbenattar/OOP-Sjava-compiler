# OOP-Sjava-compiler
israelbenattar


=============================
=      File description     =
=============================
oop.ex6.main.file.Sjavac.java - implements the Sjavac class that extract from Sjavac the lines

oop.ex6.checkfile.CheckFile.java - implements the CheckFile class that get the arrayList with all the
lines from the sSjavac.java

oop.ex6.checkfile.methods.file - file with all the methods classes and sub file with exception
that handle with method call

oop.ex6.checkfile.variable.file - file with all the variable classes and sub file with exception
that handle with variable call

oop.ex6.checkfile.exception.file - file with all the exception classes that handle with the CheckFile
exceptions


=============================
=          Design           =
=============================
You are required to specify your design, as well as your thinking process and the alternatives you
ruled out.

we try to implement the exercise modular as we cant, first we extracting from the Sjavac to arrayList with all
teh lines. the we dividing to tree part, first we check all the global field and collect the variable and the
methods names, the we taking all the methods block and check the in separate by that that we call to the method
class on every different method. in the method if we see IF or WHILE we call to the IfAndWhile class that
check that case.
and like that we Avoid from connection between the program and made it modular as we cant.


=============================
=  Implementation details   =
=============================
Please describe your design and focus on non-trivial decisions you made

we made a lot of non-trivial decisions here to handle with many cases.
we use in this exercise in data structures that inside of the we put more data structures
for example HashMap<String, string[]>> for method that the first String in the HashMap is the name
of the method and the value = String[] is all the parameters that the method should gets


=============================
=    Answers to questions   =
=============================
1)
report in your README file how you handled s-Java code errors in this exercise, and why you chose to do so.

we chose to handle with errors in the exercise by exceptions, every package have file of exception that
handle with the relevant errors in the package.
we chose to handle with exception cause we think is the best wey to handle with errors in this kind of exercise


2)
How would you modify your code to add new types of variables (e.g., float)?

we was adding to the variable package class that heiress from the variable.java and implementing in the
VariableVerifier pattern that know to recognize the new type and call to the new class in case that
he recognize it


3)
Below are four features your program currently does not support. Please select two of
them, and describe which modifications/extensions you would have to make in your code
in order to support them. Please briefly describe which classes you would add to your code,
which methods you would add to existing classes, and which classes you would modify. You
are not required to implement these features.
– Classes

we will implement one more package Classes that have similar rules like the methods but with different
rules with all that belong to variable, in general its to run with the check file mor then one time and
to save the names of public method and objects

– Different methods’ types (i.e int foo())

like in the call to method that we check that the parameters are really match to the types we will
implement new class that made the same things avery time that we assign variable that equal to the method
or that we call to the method in place that have require type, this class make sure that the method is
match to the type


– Using methods of standard java (i.e System.out.println)

we will implement class that check that the methods of standard java id valid

4)
please describe two of the main regular expressions you
used in your code.

variableAssStrPattern = this regular expressions check if the next line is match to
assignment of String variable

MethodStrPattern = this regular expressions check if the next line is match to
method call
