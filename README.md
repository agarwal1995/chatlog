# chatlog
Small Chatlog Application


This is a simple chatlog Application which stores the chatlogs for a given number of users

APIS

1.  POST: /chatlogs/{userId}
          This create a new chatlog entry for the given user.
          
2.  GET: /chatlogs/{user}
         This fetches all the chatlogs for a given user in a descending order of timestamp
         
3.  DELETE: /chatlogs/{user}
            This soft deletes all the chat for a given user
            
4.  DELETE: /chatlogs/{user}/{messageId}
            This soft delete the given messageId chatlog
