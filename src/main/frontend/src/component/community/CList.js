import CItem from "./CItem";

const CList = ({onEdit,onRemove,diaryList}) =>{
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

CList.defaultProps = {
    diaryList:[],
}
export default CList;