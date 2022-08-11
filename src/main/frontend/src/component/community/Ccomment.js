import CItem from "./CItem";

const CComment = ({onEdit,onRemove,diaryList}) =>{
    return(
        <div className="DiaryList">
            <h4>{diaryList.length}개의 댓글이 있습니다.</h4>
            <div>
                {diaryList.map((it)=> (
                    <CItem key={it.id} {...it} onRemove={onRemove} onEdit={onEdit}/>        
                ))}
            </div>
        </div>
    )
};

CComment.defaultProps = {
    diaryList:[],
}
export default CComment;