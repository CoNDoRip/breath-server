package models;

import play.db.jpa.JPA;
import javax.persistence.*;
import play.data.validation.Constraints;

import java.util.List;
import java.util.ArrayList;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * UserTask entity managed by JPA
 */
@Entity 
@SequenceGenerator(name = "usertask_seq", sequenceName = "usertask_seq")
public class UserTask implements PageView {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usertask_seq")
    public Long id; 

    @Constraints.Required
    public Long profileId;

    @Constraints.Required
    public Long taskId;
    
    @Constraints.Required
    public Date datetime;

    @Constraints.Required
    @Constraints.Min(value=0)
    public Integer approved;
    
    @Constraints.Required
    @Constraints.Min(value=0)
    public Integer rejected;

    @Constraints.Required
    public String status;

    @Constraints.Required
    @Constraints.MaxLength(value=100)
    public String image;
    
    @Constraints.Required
    @Constraints.Min(value=0)
    public Integer liked;

    public UserTask() {
    }

    /**
    * Default constructor for adding new UserTask
    * that set profileId, taskId and name of image
    */
    public UserTask(Long profileId, Long taskId, String imageName) {
        this.profileId = profileId;
        this.taskId = taskId;
        this.datetime = new Date();
        this.approved = 0;
        this.rejected = 0;
        this.status = UserTaskStatus.PENDING.getStatus();
        this.image = imageName;
        this.liked = 0;
    }

    /**
    * Copy constructor
    */
    public UserTask(UserTask ut) {
        this.id = ut.id;
        this.profileId = ut.profileId;
        this.taskId = ut.taskId;
        this.datetime = ut.datetime;
        this.approved = ut.approved;
        this.rejected = ut.rejected;
        this.status = ut.status;
        this.image = ut.image;
        this.liked = ut.liked;
    }

    /**
     * Find a UserTask by id.
     */
    public static UserTask findById(Long id) {
        return JPA.em().find(UserTask.class, id);
    }

    /**
    *
    */
    public static UserTask findByProfileIdAndTaskId(Long profileId, Long taskId) {
        return (UserTask) JPA.em()
            .createQuery("from UserTask where profileId = :pi and taskId = :ti")
            .setParameter("pi", profileId)
            .setParameter("ti", taskId)
            .getSingleResult();
    }

    /**
    * Find all UserTasks by profileId
    */
    public static List<UserTaskWithTitle> findByProfileId(Long profileId, Integer page) {
        List<UserTask> listOfUserTasks = JPA.em()
            .createQuery("from UserTask where profileId = :pi order by datetime desc")
            .setParameter("pi", profileId)
            .setFirstResult((page - 1) * PAGESIZE)
            .setMaxResults(PAGESIZE)
            .getResultList();

        return UserTaskWithTitle.getListOfUserTasksWithTitle(listOfUserTasks);
    }

    public void checkStatus() {
        if(this.approved > 5 && this.approved > this.rejected * 3) {
            this.status = UserTaskStatus.APPROVED.getStatus();
            Profile profile = Profile.findById(this.profileId);
            profile.completed++;
            profile.points += 100 * profile.level;
            Level currLevel = Level.findById(profile.level);
            if(profile.points >= currLevel.needToNextLevel) {
                profile.level++;
            }
            profile.update();
        } else if(this.rejected > 5 && this.rejected > this.approved * 3) {
            this.status = UserTaskStatus.REJECTED.getStatus();
        }
    }
    
    /**
     * Insert this new UserTask.
     */
    public void save() {
        this.id = id;
        JPA.em().persist(this);
    }

    /**
     * Update this UserTask.
     */
    public void update() {
        JPA.em().merge(this);
    }
    
    /**
     * Delete this UserTask.
     */
    public void delete() {
        JPA.em().remove(this);
    }

    /**
    * Enumeration of UserTask statuses
    */
    public enum UserTaskStatus {
        
          REJECTED("rejected")
        , PENDING("pending")
        , APPROVED("approved");
        ;

        private String status;

        UserTaskStatus(String status) {this.status = status;}

        public String getStatus() {return status;}
    }

    /**
    * Special form of user task with title of task
    */
    public static class UserTaskWithTitle extends UserTask {

        public String title;

        UserTaskWithTitle(UserTask ut) {
            super(ut);
            title = (String) JPA.em()
                .createQuery("select title from Task where id = ?")
                .setParameter(1, ut.taskId)
                .getSingleResult();
        }

        public static List<UserTaskWithTitle> getListOfUserTasksWithTitle(
                                            List<UserTask> listOfUserTasks) {
            List<UserTaskWithTitle> listOfUserTasksWithTitle
                = new ArrayList<UserTaskWithTitle>(PAGESIZE);

            for(UserTask ut: listOfUserTasks)
                listOfUserTasksWithTitle.add(new UserTaskWithTitle(ut));

            return listOfUserTasksWithTitle;
        }
    }

}
