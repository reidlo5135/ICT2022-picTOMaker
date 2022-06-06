import React from "react";

const Pagination = ({ postsPerPage, totalPosts, paginate }) => {
  const pageNumbers = [];
  for (let i = 1; i <= Math.ceil(totalPosts / postsPerPage); i++) {
    pageNumbers.push(i);
  }
  return (
    <div>
      <nav className="pagenation">
          {pageNumbers.map((number) => (
            <li key={number} className="page-item">
              <div className='pagespan' onClick={() => paginate(number)}>
                {number}
              </div>
            </li>
          ))}
      </nav>
    </div>
  );
};

export default Pagination;