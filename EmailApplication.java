package email.com;

import java.util.Scanner;
import java.security.SecureRandom;

//Email class representing an email account
class Email {
 private String username;
 private String password;
 private int mailboxCapacity;
 private String alternateEmail;

 // Constructor
 public Email(String username, String password) {
     this.username = username;
     this.password = password;
     this.mailboxCapacity = 100; // Default mailbox capacity
     this.alternateEmail = "";
 }

 // Getter and setter for mailbox capacity
 public int getMailboxCapacity() {
     return this.mailboxCapacity;
 }

 public void setMailboxCapacity(int capacity) {
     this.mailboxCapacity = capacity;
 }

 // Getter and setter for alternate email
 public String getAlternateEmail() {
     return this.alternateEmail;
 }

 public void setAlternateEmail(String alternateEmail) {
     this.alternateEmail = alternateEmail;
 }

 // Method to change the password
 public void changePassword(String newPassword) {
     this.password = newPassword;
     System.out.println("Password changed successfully");
 }

 // Method to generate a random password
 public String generateRandomPassword(int length) {
     SecureRandom random = new SecureRandom();

     // Define character sets for each pattern requirement
     String uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
     String lowercaseChars = "abcdefghijklmnopqrstuvwxyz";
     String digitChars = "0123456789";
     String specialChars = "#@$*&";

     // Ensure each character set is represented in the password
     StringBuilder randomPassword = new StringBuilder();
     randomPassword.append(uppercaseChars.charAt(random.nextInt(uppercaseChars.length())));
     randomPassword.append(lowercaseChars.charAt(random.nextInt(lowercaseChars.length())));
     randomPassword.append(digitChars.charAt(random.nextInt(digitChars.length())));
     randomPassword.append(specialChars.charAt(random.nextInt(specialChars.length())));

     // Fill the rest of the password with random characters from all sets
     String allChars = uppercaseChars + lowercaseChars + digitChars + specialChars;
     for (int i = 4; i < length; i++) {
         randomPassword.append(allChars.charAt(random.nextInt(allChars.length())));
     }

     // Shuffle the password to make it more random
     char[] passwordArray = randomPassword.toString().toCharArray();
     for (int i = passwordArray.length - 1; i > 0; i--) {
         int index = random.nextInt(i + 1);
         char temp = passwordArray[index];
         passwordArray[index] = passwordArray[i];
         passwordArray[i] = temp;
     }

     return new String(passwordArray);
 }

     // Display account information
 public void displayAccountInfo() {
     System.out.println("Username: " + this.username );
     System.out.println("Password: " + this.password);
     System.out.println("Mailbox Capacity: " + this.mailboxCapacity);
     System.out.println("Alternate Email: " + this.alternateEmail);
 }
}


public class EmailApplication {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);

        // Get user input for username and password
		String username;
        boolean isValidUsername;
        do {
            System.out.print("Enter your email as username: ");
            
            username = scanner.next();
            isValidUsername = isValidEmail(username);
            if (!isValidUsername) {
                System.out.println("Invalid email username! Please make sure it is in the correct pattern.");
                System.out.println();
            }
        } while (!isValidUsername);

        
//        System.out.print("Enter your email password: ");
//        String password = scanner.next();
        
        String password;
        boolean isValidPassword;
        
        do {
            System.out.print("Enter your password: ");
            password = scanner.next();
            isValidPassword = isValidPassword(password);
            if (!isValidPassword) {
                System.out.println("Invalid password! Please make sure the password meets the criteria.");
                System.out.println();
            }
        } while (!isValidPassword(password));

        System.out.println();
        // Create an Email object
        Email myEmail = new Email(username, password);

        // Display initial account information
        //System.out.println();
        System.out.println("-----------------------------");
        System.out.println("Initial Account Information:");
        System.out.println("-----------------------------");
        myEmail.displayAccountInfo();

        // Get user input for alternate email
        String alternateEmail;
        boolean isValidAlternateEmail;
        do {
            System.out.print("Enter alternate email address : ");
            alternateEmail = scanner.next();
            isValidAlternateEmail = isValidAlternateEmail(alternateEmail, username);
            if (!isValidAlternateEmail) {
                if (alternateEmail.equals(username)) {
                    System.out.println("Error: Alternate email cannot be the same as the username.");
                    System.out.println();
                } else {
                    System.out.println("Invalid alternate email address! Please make sure it is in the correct pattern and different from the username.");
                    System.out.println();
                }
            }
        } while (!isValidAlternateEmail);

        if (!alternateEmail.isEmpty()) {
            myEmail.setAlternateEmail(alternateEmail);
        }


        // Generate and set a random password
        String newPassword = myEmail.generateRandomPassword(8);
        myEmail.changePassword(newPassword);

        // Display updated account information
        System.out.println();
        System.out.println("-----------------------------");
        System.out.println("Updated Account Information:");
        System.out.println("-----------------------------");
        myEmail.displayAccountInfo();

        // Close the scanner
        scanner.close();

	}

	private static boolean isValidPassword(String password) {
		String lengthRegex = ".{8,12}";
		String uppercaseRegex = ".*[A-Z].*";
        String lowercaseRegex = ".*[a-z].*";
        String digitRegex = ".*\\d.*";
        String specialCharRegex = ".*[#@$*&].*";

		return  password.matches(lengthRegex) &&
				password.matches(uppercaseRegex) &&
                password.matches(lowercaseRegex) &&
                password.matches(digitRegex) &&
                password.matches(specialCharRegex);
	}
	
	  private static boolean isValidEmail(String email) {
	        // Define criteria for a valid email username
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

	        // Check if the email username meets the criteria
	        return email.matches(emailRegex);
	    }
	
	// Method to check if an alternate email is in the correct pattern
	  private static boolean isValidAlternateEmail(String alternateEmail, String username) {
	        // Define criteria for a valid alternate email
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

	        // Check if the alternate email meets the criteria and is different from the username
	        return alternateEmail.isEmpty() || (alternateEmail.matches(emailRegex) && !alternateEmail.equals(username));
	    }
	  

}
