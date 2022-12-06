USE `project_manager`;
SELECT * 
FROM MEMBER AS M, TEAM AS T
INNER JOIN `ADMIN` AS A ON T.Lead_Email = A.Email
WHERE M.Email = A.Email;