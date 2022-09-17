import CommunityItem from "./CommunityItem";

const CommunityComment = ({onEdit,onRemove,diaryList}) =>{
    return(
        <div className="DiaryList">
            <h4>{diaryList.length}개의 댓글이 있습니다.</h4>
            <div>
                {diaryList.map((it)=> (
                    <CommunityItem key={it.id} {...it} onRemove={onRemove} onEdit={onEdit}/>
                ))}
            </div>
        </div>
    )
};

CommunityComment.defaultProps = {
    diaryList:[],
}
export default CommunityComment;