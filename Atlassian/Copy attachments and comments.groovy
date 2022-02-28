import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.link.IssueLink
import com.atlassian.jira.issue.comments.CommentManager
import com.atlassian.jira.issue.attachment.Attachment

def issue = event.issue
def issueService = ComponentAccessor.getIssueService()
def user = ComponentAccessor.getUserManager().getUserByKey("Admin");
def linkMgr = ComponentAccessor.getIssueLinkManager()
def links = ComponentAccessor.getIssueLinkManager().getOutwardLinks(issue.getId())
def attachmentManager = ComponentAccessor.getAttachmentManager()
def sortAttachment = attachmentManager.getAttachments(issue).sort { it.created }


// obtengo el KEY del issue que disparó el evento
def EventIssue = event.issue
def lastcomment = event.getComment()
def commentBody = lastcomment.getBody()
def commentAuthor = lastcomment.getAuthorApplicationUser()
def commentAuthorName= user.getDisplayName()

def CommentManager commentManager = ComponentAccessor.getCommentManager()

if(commentBody.contains('#')){
    log.warn("Do nothing")
}else if(commentBody.contains('©')){
    log.warn("Do nothing")
}else if(!commentBody.contains('#') && !(commentBody.contains('.pdf') || commentBody.contains('.csv') || commentBody.contains('.txt') || 
                                         commentBody.contains('.png') || commentBody.contains('.jpg') || commentBody.contains('.html') ||
                                         commentBody.contains('.PDF') || commentBody.contains('.xls') || commentBody.contains('.doc') ||
                                         commentBody.contains('.docx') || commentBody.contains('.xlsx') || commentBody.contains('.ppt') ||
                                         commentBody.contains('.pptx') || commentBody.contains('.log') || commentBody.contains('.PNG') ||
                                         commentBody.contains('.PPTX') || commentBody.contains('.DOCX') || commentBody.contains('.PPT') ||
                                         commentBody.contains('.CSV') || commentBody.contains('.LOG') || commentBody.contains('.TXT') ||
                                         commentBody.contains('.XLS') || commentBody.contains('.HTML') || commentBody.contains('.XLSX'))){
    
    for (IssueLink link in linkMgr.getOutwardLinks(EventIssue.id))
        		{
                        def tipodelink = link.issueLinkType.outward

                        if (tipodelink == "relates to")
                        {  

                            def LinkedIssue = link.getDestinationObject()                   

                                // to avoid infinit loop
                                commentBody = "{color:#0747A6} El usuario *"+ commentAuthor.getDisplayName() +"* comentó: {color}" + commentBody + " #"            	
                                commentManager.create(LinkedIssue, user,commentBody, true)

                        } 

                }	
    //in
    for (IssueLink link in linkMgr.getInwardLinks(EventIssue.id))
    {
        def tipodelink = link.issueLinkType.inward

        if (tipodelink == "relates to")
        {  

            def LinkedIssue = link.getSourceObject()                   

            //to avoid infinit loop
            commentBody = "{color:#0747A6} El usuario *"+ commentAuthor.getDisplayName() +"* comentó: {color}" + commentBody + " #"            	
            commentManager.create(LinkedIssue, user,commentBody, true)

        } 

    }	
}else if(!commentBody.contains('©') && (commentBody.contains('.pdf') || commentBody.contains('.csv') || commentBody.contains('.txt') || 
                                         commentBody.contains('.png') || commentBody.contains('.jpg') || commentBody.contains('.html') ||
                                         commentBody.contains('.PDF') || commentBody.contains('.xls') || commentBody.contains('.doc') ||
                                         commentBody.contains('.docx') || commentBody.contains('.xlsx') || commentBody.contains('.ppt') ||
                                         commentBody.contains('.pptx') || commentBody.contains('.log') || commentBody.contains('.PNG') ||
                                         commentBody.contains('.PPTX') || commentBody.contains('.DOCX') || commentBody.contains('.PPT') ||
                                         commentBody.contains('.CSV') || commentBody.contains('.LOG') || commentBody.contains('.TXT') ||
                                         commentBody.contains('.XLS') || commentBody.contains('.HTML') || commentBody.contains('.XLSX'))){
    def lastAttachment = sortAttachment.last()
    			for (IssueLink link in linkMgr.getOutwardLinks(EventIssue.id))
        		{
                    def tipodelink = link.issueLinkType.outward
                    if (tipodelink == "relates to")
                    {  

                                def LinkedIssue = link.getDestinationObject()
                                    if (lastAttachment) { 
                                        attachmentManager.copyAttachment(lastAttachment, commentAuthor, link.getDestinationObject().key)
                                          // to avoid infinit loop
                                        commentBody = "{color:#0747A6} El usuario *"+ commentAuthor.getDisplayName() +"* comentó: {color}" + commentBody + " ©#"            	
                                        commentManager.create(LinkedIssue, user,commentBody, true)

                                     }

                                  
                    } 
                }
    //in
    for (IssueLink link in linkMgr.getInwardLinks(EventIssue.id))
    {
        def tipodelink = link.issueLinkType.inward
        if (tipodelink == "relates to")
        {  

            def LinkedIssue = link.getSourceObject()
            if (lastAttachment) { 
                attachmentManager.copyAttachment(lastAttachment, commentAuthor, link.getSourceObject().key)
                // to avoid infinit loop
                commentBody = "{color:#0747A6} El usuario *"+ commentAuthor.getDisplayName() +"* comentó: {color}" + commentBody + " ©#"            	
                commentManager.create(LinkedIssue, user,commentBody, true)

            }


        } 
    }
}else if(!commentBody.contains('#') && (commentBody.contains('.pdf') || commentBody.contains('.csv') || commentBody.contains('.txt') || 
                                         commentBody.contains('.png') || commentBody.contains('.jpg') || commentBody.contains('.html') ||
                                         commentBody.contains('.PDF') || commentBody.contains('.xls') || commentBody.contains('.doc') ||
                                         commentBody.contains('.docx') || commentBody.contains('.xlsx') || commentBody.contains('.ppt') ||
                                         commentBody.contains('.pptx') || commentBody.contains('.log') || commentBody.contains('.PNG') ||
                                         commentBody.contains('.PPTX') || commentBody.contains('.DOCX') || commentBody.contains('.PPT') ||
                                         commentBody.contains('.CSV') || commentBody.contains('.LOG') || commentBody.contains('.TXT') ||
                                         commentBody.contains('.XLS') || commentBody.contains('.HTML') || commentBody.contains('.XLSX'))){
    def lastAttachment = sortAttachment.last()
    for (IssueLink link in linkMgr.getOutwardLinks(EventIssue.id))
    {
        def tipodelink = link.issueLinkType.outward

        if (tipodelink == "relates to")
        {  

            def LinkedIssue = link.getDestinationObject()
            if (lastAttachment) { 
                attachmentManager.copyAttachment(lastAttachment, commentAuthor, link.getDestinationObject().key)
                // to avoid infinit loop
                commentBody = "{color:#0747A6} El usuario *"+ commentAuthor.getDisplayName() +"* comentó: {color}" + commentBody + " ©#"            	
                commentManager.create(LinkedIssue, user,commentBody, true)

            }


        } 

    }	
    
    //in
     for (IssueLink link in linkMgr.getInwardLinks(EventIssue.id))
        		{
                        def tipodelink = link.issueLinkType.inward

                         if (tipodelink == "relates to")
                            {  

                                        def LinkedIssue = link.getSourceObject()
                                            if (lastAttachment) { 
                                                attachmentManager.copyAttachment(lastAttachment, commentAuthor, link.getSourceObject().key)
                                                  //to avoid infinit loop
                                                commentBody = "{color:#0747A6} El usuario *"+ commentAuthor.getDisplayName() +"* comentó: {color}" + commentBody + " ©#"            	
                                                commentManager.create(LinkedIssue, user,commentBody, true)

                                             }


                            } 

                }
}

//----------------------------------------------------------
